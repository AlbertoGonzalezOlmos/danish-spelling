package com.example.danishspelling.service

import android.content.Context
import android.util.Log
import android.view.textservice.SentenceSuggestionsInfo
import android.view.textservice.SpellCheckerSession
import android.view.textservice.SuggestionsInfo
import android.view.textservice.TextInfo
import android.view.textservice.TextServicesManager
import com.example.danishspelling.util.DanishTextUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

data class SpellCheckResult(
    val word: String,
    val offset: Int,
    val length: Int,
    val suggestions: List<String>
)

@Singleton
class DanishSpellCheckService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "DanishSpellCheck"
        private const val TIMEOUT_MS = 10_000L
        private const val MAX_SUGGESTIONS = 5
    }

    /**
     * Check the given text for spelling errors using Android's SpellCheckerSession
     * configured for Danish. Returns a list of misspelled words with suggestions.
     *
     * Falls back to the system default spell checker if no Danish-specific one exists.
     * Returns an error message as the first element if something goes wrong (word = error indicator).
     */
    suspend fun checkText(text: String): SpellCheckOutcome {
        if (text.isBlank()) return SpellCheckOutcome.Success(emptyList())

        val tsm = context.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE)
                as? TextServicesManager
        if (tsm == null) {
            Log.e(TAG, "TextServicesManager not available")
            return SpellCheckOutcome.Error("Spell check service not available on this device.")
        }

        // Try Danish locale first, then fall back to system default
        val result = trySpellCheck(tsm, text, DanishTextUtils.getDanishLocale())
            ?: trySpellCheck(tsm, text, null)

        return result ?: SpellCheckOutcome.Error(
            "No spell checker available. Install a keyboard app with spell check support (e.g. Gboard)."
        )
    }

    private suspend fun trySpellCheck(
        tsm: TextServicesManager,
        text: String,
        locale: Locale?
    ): SpellCheckOutcome? {
        val label = locale?.toString() ?: "system-default"

        return withTimeoutOrNull(TIMEOUT_MS) {
            withContext(Dispatchers.Main) {
                suspendCancellableCoroutine { continuation ->
                    val session = try {
                        if (locale != null) {
                            tsm.newSpellCheckerSession(null, locale, object :
                                SpellCheckerSession.SpellCheckerSessionListener {
                                override fun onGetSuggestions(r: Array<out SuggestionsInfo>?) {}
                                override fun onGetSentenceSuggestions(r: Array<out SentenceSuggestionsInfo>?) {}
                            }, false)
                        } else {
                            tsm.newSpellCheckerSession(null, Locale.getDefault(), object :
                                SpellCheckerSession.SpellCheckerSessionListener {
                                override fun onGetSuggestions(r: Array<out SuggestionsInfo>?) {}
                                override fun onGetSentenceSuggestions(r: Array<out SentenceSuggestionsInfo>?) {}
                            }, true)
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "Failed to probe session for $label", e)
                        null
                    }

                    // Probe session isn't usable for actual checking; close it and create a real one
                    session?.close()

                    if (session == null) {
                        Log.d(TAG, "No spell checker session for locale: $label")
                        if (continuation.isActive) continuation.resume(null)
                        return@suspendCancellableCoroutine
                    }

                    Log.d(TAG, "Spell checker available for $label, running word-level check")
                    // Use word-level getSuggestions which is more widely supported
                    runWordLevelCheck(tsm, text, locale, continuation)
                }
            }
        }
    }

    /**
     * Word-level spell check: split text into words, send each as a TextInfo
     * via getSuggestions(). This is more compatible than getSentenceSuggestions().
     */
    private fun runWordLevelCheck(
        tsm: TextServicesManager,
        fullText: String,
        locale: Locale?,
        continuation: kotlinx.coroutines.CancellableContinuation<SpellCheckOutcome?>
    ) {
        // Parse words with their offsets in the original text
        val wordPattern = Regex("""[\p{L}\p{M}''-]+""")
        val wordEntries = wordPattern.findAll(fullText).map { match ->
            WordEntry(match.value, match.range.first)
        }.toList()

        if (wordEntries.isEmpty()) {
            if (continuation.isActive) continuation.resume(SpellCheckOutcome.Success(emptyList()))
            return
        }

        val textInfos = wordEntries.mapIndexed { index, entry ->
            TextInfo(entry.word, 0, index)
        }.toTypedArray()

        val listener = object : SpellCheckerSession.SpellCheckerSessionListener {
            private var spellSession: SpellCheckerSession? = null
            private var receivedCount = 0
            private val results = mutableListOf<SpellCheckResult>()

            fun attachSession(s: SpellCheckerSession) {
                spellSession = s
            }

            override fun onGetSuggestions(suggestionsArray: Array<out SuggestionsInfo>?) {
                Log.d(TAG, "onGetSuggestions called, count=${suggestionsArray?.size}")
                suggestionsArray?.forEachIndexed { index, info ->
                    processWordResult(index, info)
                }
                receivedCount += suggestionsArray?.size ?: 0
                checkComplete()
            }

            override fun onGetSentenceSuggestions(r: Array<out SentenceSuggestionsInfo>?) {
                // Not used in word-level mode
            }

            private fun processWordResult(index: Int, info: SuggestionsInfo) {
                if (index >= wordEntries.size) return
                val entry = wordEntries[index]
                val attrs = info.suggestionsAttributes

                val inDictionary = attrs and SuggestionsInfo.RESULT_ATTR_IN_THE_DICTIONARY != 0
                val looksLikeTypo = attrs and SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO != 0

                Log.d(TAG, "Word '${entry.word}': attrs=$attrs, inDict=$inDictionary, typo=$looksLikeTypo, suggestions=${info.suggestionsCount}")

                if (inDictionary) return
                if (!looksLikeTypo && info.suggestionsCount == 0) return

                val suggestions = (0 until info.suggestionsCount)
                    .map { info.getSuggestionAt(it) }

                results.add(
                    SpellCheckResult(
                        word = entry.word,
                        offset = entry.offset,
                        length = entry.word.length,
                        suggestions = suggestions
                    )
                )
            }

            private fun checkComplete() {
                if (receivedCount >= wordEntries.size) {
                    spellSession?.close()
                    if (continuation.isActive) {
                        continuation.resume(SpellCheckOutcome.Success(results.toList()))
                    }
                }
            }
        }

        val session = try {
            if (locale != null) {
                tsm.newSpellCheckerSession(null, locale, listener, false)
            } else {
                tsm.newSpellCheckerSession(null, Locale.getDefault(), listener, true)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create spell checker session", e)
            if (continuation.isActive) continuation.resume(null)
            return
        }

        if (session == null) {
            Log.w(TAG, "Spell checker session is null")
            if (continuation.isActive) continuation.resume(null)
            return
        }

        listener.attachSession(session)

        continuation.invokeOnCancellation {
            session.close()
        }

        // Send words in batches — some spell checkers limit batch size
        val batchSize = 50
        for (i in textInfos.indices step batchSize) {
            val batch = textInfos.sliceArray(i until minOf(i + batchSize, textInfos.size))
            @Suppress("DEPRECATION")
            session.getSuggestions(batch, MAX_SUGGESTIONS, false)
        }
    }

    private data class WordEntry(val word: String, val offset: Int)
}

sealed class SpellCheckOutcome {
    data class Success(val results: List<SpellCheckResult>) : SpellCheckOutcome()
    data class Error(val message: String) : SpellCheckOutcome()
}
