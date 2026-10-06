package com.example.danishspelling.presentation.parent

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.danishspelling.R
import kotlin.Int
import kotlin.Long

public class ListManagementFragmentDirections private constructor() {
  private data class ActionToSentences(
    public val listId: Long,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_to_sentences

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putLong("listId", this.listId)
        return result
      }
  }

  public companion object {
    public fun actionToSentences(listId: Long): NavDirections = ActionToSentences(listId)
  }
}
