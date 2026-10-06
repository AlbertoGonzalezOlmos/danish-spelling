package com.example.danishspelling.util

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import com.example.danishspelling.service.SpellCheckResult

object SpellCheckHighlighter {

    private const val HIGHLIGHT_COLOR = 0x44FF0000 // Semi-transparent red background

    /**
     * Apply spell-check highlighting to the EditText.
     * Misspelled words get a red-tinted background.
     */
    fun applyHighlights(editText: EditText, results: List<SpellCheckResult>) {
        val text = editText.text
        if (text == null || results.isEmpty()) return

        // Clear existing spell-check spans
        clearHighlights(editText)

        for (result in results) {
            val start = result.offset
            val end = result.offset + result.length
            if (start < 0 || end > text.length || start >= end) continue

            text.setSpan(
                BackgroundColorSpan(HIGHLIGHT_COLOR),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    /**
     * Remove all spell-check highlight spans from the EditText.
     */
    fun clearHighlights(editText: EditText) {
        val text = editText.text ?: return
        val spans = text.getSpans(0, text.length, BackgroundColorSpan::class.java)
        for (span in spans) {
            text.removeSpan(span)
        }
    }

    /**
     * Build a summary string of misspelled words and their suggestions.
     */
    fun buildSuggestionsSummary(results: List<SpellCheckResult>): String {
        if (results.isEmpty()) return "No spelling issues found."

        return buildString {
            append("Found ${results.size} possible issue(s):\n\n")
            for (result in results) {
                append("\"${result.word}\"")
                if (result.suggestions.isNotEmpty()) {
                    append(" -> ${result.suggestions.joinToString(", ")}")
                }
                append("\n")
            }
        }
    }
}
