package com.example.danishspelling.util

import java.util.Locale

object DanishTextUtils {
    private val danishChars = setOf('æ', 'ø', 'å', 'Æ', 'Ø', 'Å')

    /**
     * Normalize Danish text for comparison
     * - Convert to lowercase
     * - Trim whitespace
     * - Collapse multiple spaces to single space
     * - Preserve Danish characters (æ, ø, å)
     */
    fun normalizeDanish(text: String): String {
        return text.trim()
            .lowercase(Locale("da", "DK"))
            .replace(Regex("\\s+"), " ")
    }

    /**
     * Check if a character is a Danish-specific character
     */
    fun isDanishChar(char: Char): Boolean {
        return char in danishChars
    }

    /**
     * Get the Danish locale
     */
    fun getDanishLocale(): Locale {
        return Locale("da", "DK")
    }

    /**
     * Remove punctuation from text (useful for comparing sentences)
     */
    fun removePunctuation(text: String): String {
        return text.replace(Regex("[.,!?;:]"), "")
    }

    /**
     * Normalize for strict comparison (removes punctuation too)
     */
    fun normalizeForComparison(text: String): String {
        return normalizeDanish(removePunctuation(text))
    }
}
