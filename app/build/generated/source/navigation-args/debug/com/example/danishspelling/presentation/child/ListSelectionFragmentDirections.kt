package com.example.danishspelling.presentation.child

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.danishspelling.R
import kotlin.Boolean
import kotlin.Int
import kotlin.Long

public class ListSelectionFragmentDirections private constructor() {
  private data class ActionToPractice(
    public val listId: Long,
    public val randomOrder: Boolean = false,
    public val incorrectOnly: Boolean = false,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_to_practice

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putLong("listId", this.listId)
        result.putBoolean("randomOrder", this.randomOrder)
        result.putBoolean("incorrectOnly", this.incorrectOnly)
        return result
      }
  }

  public companion object {
    public fun actionToPractice(
      listId: Long,
      randomOrder: Boolean = false,
      incorrectOnly: Boolean = false,
    ): NavDirections = ActionToPractice(listId, randomOrder, incorrectOnly)
  }
}
