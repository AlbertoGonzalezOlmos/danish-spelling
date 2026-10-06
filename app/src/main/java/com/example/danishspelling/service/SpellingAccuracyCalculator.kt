package com.example.danishspelling.service

import com.example.danishspelling.util.Constants
import com.example.danishspelling.util.DanishTextUtils
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.min

@Singleton
class SpellingAccuracyCalculator @Inject constructor() {

    /**
     * Calculate spelling accuracy using Levenshtein distance algorithm
     * Returns accuracy as a percentage (0-100)
     */
    fun calculateAccuracy(userInput: String, correctText: String): Float {
        // Normalize both strings for comparison
        val normalized1 = DanishTextUtils.normalizeForComparison(userInput)
        val normalized2 = DanishTextUtils.normalizeForComparison(correctText)

        // Perfect match
        if (normalized1 == normalized2) return 100f

        // Calculate edit distance
        val distance = levenshteinDistance(normalized1, normalized2)
        val maxLength = max(normalized1.length, normalized2.length)

        // Avoid division by zero
        if (maxLength == 0) return 100f

        // Calculate accuracy percentage
        val accuracy = ((maxLength - distance).toFloat() / maxLength * 100)
        return accuracy.coerceIn(0f, 100f)
    }

    /**
     * Convert accuracy to star rating (0-3 stars)
     */
    fun calculateStars(accuracy: Float): Int = when {
        accuracy >= Constants.THREE_STAR_THRESHOLD -> 3
        accuracy >= Constants.TWO_STAR_THRESHOLD -> 2
        accuracy >= Constants.ONE_STAR_THRESHOLD -> 1
        else -> 0
    }

    /**
     * Get feedback level based on accuracy
     */
    fun getFeedbackLevel(accuracy: Float): FeedbackLevel = when {
        accuracy >= Constants.THREE_STAR_THRESHOLD -> FeedbackLevel.PERFECT
        accuracy >= Constants.TWO_STAR_THRESHOLD -> FeedbackLevel.GREAT
        accuracy >= Constants.ONE_STAR_THRESHOLD -> FeedbackLevel.GOOD
        accuracy >= 40f -> FeedbackLevel.ALMOST
        else -> FeedbackLevel.TRY_AGAIN
    }

    /**
     * Levenshtein distance algorithm implementation
     * Calculates the minimum number of single-character edits (insertions, deletions, substitutions)
     * required to change one string into another
     */
    private fun levenshteinDistance(s1: String, s2: String): Int {
        val len1 = s1.length
        val len2 = s2.length

        // Create a matrix to store distances
        val dp = Array(len1 + 1) { IntArray(len2 + 1) }

        // Initialize first column (deletions)
        for (i in 0..len1) {
            dp[i][0] = i
        }

        // Initialize first row (insertions)
        for (j in 0..len2) {
            dp[0][j] = j
        }

        // Fill the matrix
        for (i in 1..len1) {
            for (j in 1..len2) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1

                dp[i][j] = min(
                    min(
                        dp[i - 1][j] + 1,      // deletion
                        dp[i][j - 1] + 1       // insertion
                    ),
                    dp[i - 1][j - 1] + cost    // substitution
                )
            }
        }

        return dp[len1][len2]
    }

    /**
     * Get simple suggestion for improvement (for future enhancement)
     */
    fun getSuggestion(userInput: String, correctText: String): String? {
        val normalized1 = DanishTextUtils.normalizeForComparison(userInput)
        val normalized2 = DanishTextUtils.normalizeForComparison(correctText)

        return when {
            normalized1.length < normalized2.length -> "Your answer is too short"
            normalized1.length > normalized2.length -> "Your answer is too long"
            else -> null
        }
    }
}

enum class FeedbackLevel {
    PERFECT,    // 95%+
    GREAT,      // 80-94%
    GOOD,       // 60-79%
    ALMOST,     // 40-59%
    TRY_AGAIN   // <40%
}
