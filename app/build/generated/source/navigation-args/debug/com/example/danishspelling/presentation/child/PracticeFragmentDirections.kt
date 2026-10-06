package com.example.danishspelling.presentation.child

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.danishspelling.R
import kotlin.Float
import kotlin.Int

public class PracticeFragmentDirections private constructor() {
  private data class ActionToReward(
    public val totalStars: Int,
    public val accuracy: Float,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_to_reward

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("totalStars", this.totalStars)
        result.putFloat("accuracy", this.accuracy)
        return result
      }
  }

  public companion object {
    public fun actionToReward(totalStars: Int, accuracy: Float): NavDirections =
        ActionToReward(totalStars, accuracy)
  }
}
