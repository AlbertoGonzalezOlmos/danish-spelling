package com.example.danishspelling.service

import com.example.danishspelling.R
import com.example.danishspelling.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardSystem @Inject constructor() {

    /**
     * Get encouragement message based on feedback level
     */
    fun getEncouragementMessage(feedbackLevel: FeedbackLevel): Int = when (feedbackLevel) {
        FeedbackLevel.PERFECT -> R.string.encouragement_perfect
        FeedbackLevel.GREAT -> R.string.encouragement_fantastic
        FeedbackLevel.GOOD -> R.string.encouragement_great
        FeedbackLevel.ALMOST -> R.string.encouragement_good_try
        FeedbackLevel.TRY_AGAIN -> R.string.encouragement_try_again
    }

    /**
     * Get encouragement message based on accuracy
     */
    fun getEncouragementMessageForAccuracy(accuracy: Float): Int = when {
        accuracy >= Constants.THREE_STAR_THRESHOLD -> R.string.encouragement_perfect
        accuracy >= Constants.TWO_STAR_THRESHOLD -> R.string.encouragement_fantastic
        accuracy >= Constants.ONE_STAR_THRESHOLD -> R.string.encouragement_great
        accuracy >= 40f -> R.string.encouragement_good_try
        else -> R.string.encouragement_try_again
    }

    /**
     * Calculate reward based on accuracy and attempt number
     */
    fun calculateReward(accuracy: Float, attemptNumber: Int, stars: Int): Reward {
        val message = getEncouragementMessageForAccuracy(accuracy)
        val animationType = when {
            stars >= 3 -> AnimationType.CELEBRATION
            stars >= 2 -> AnimationType.HAPPY
            stars >= 1 -> AnimationType.ENCOURAGING
            else -> AnimationType.TRY_AGAIN
        }

        return Reward(
            stars = stars,
            messageResId = message,
            animationType = animationType,
            shouldPlaySound = true,
            bonusMessage = getBonusMessage(accuracy, attemptNumber)
        )
    }

    /**
     * Get bonus message for special achievements
     */
    private fun getBonusMessage(accuracy: Float, attemptNumber: Int): String? {
        return when {
            accuracy == 100f && attemptNumber == 1 -> "Perfect på første forsøg! 🌟"
            accuracy >= 95f -> "Næsten perfekt!"
            attemptNumber == 1 && accuracy >= 80f -> "Godt klaret første gang!"
            else -> null
        }
    }

    /**
     * Check if milestone achievement was reached
     */
    fun checkMilestone(totalStars: Int): Achievement? {
        return when (totalStars) {
            10 -> Achievement(
                title = "Første 10 stjerner!",
                description = "Du er godt i gang!",
                icon = "⭐"
            )
            50 -> Achievement(
                title = "50 stjerner!",
                description = "Fantastisk arbejde!",
                icon = "🌟"
            )
            100 -> Achievement(
                title = "100 stjerner!",
                description = "Du er en stavemester!",
                icon = "🏆"
            )
            250 -> Achievement(
                title = "250 stjerner!",
                description = "Utroligt! Bliver du ved!",
                icon = "🎖️"
            )
            500 -> Achievement(
                title = "500 stjerner!",
                description = "Du er en sand mester!",
                icon = "👑"
            )
            else -> null
        }
    }

    /**
     * Calculate streak bonus
     */
    fun calculateStreakBonus(streakDays: Int): Int {
        return when {
            streakDays >= 7 -> 5  // Bonus stars for weekly streak
            streakDays >= 3 -> 2  // Bonus stars for 3-day streak
            else -> 0
        }
    }
}

data class Reward(
    val stars: Int,
    val messageResId: Int,
    val animationType: AnimationType,
    val shouldPlaySound: Boolean,
    val bonusMessage: String? = null
)

data class Achievement(
    val title: String,
    val description: String,
    val icon: String
)

enum class AnimationType {
    CELEBRATION,     // For 3 stars
    HAPPY,           // For 2 stars
    ENCOURAGING,     // For 1 star
    TRY_AGAIN       // For 0 stars
}
