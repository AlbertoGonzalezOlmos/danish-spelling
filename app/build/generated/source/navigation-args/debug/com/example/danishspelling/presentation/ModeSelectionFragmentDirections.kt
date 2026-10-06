package com.example.danishspelling.presentation

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.danishspelling.R

public class ModeSelectionFragmentDirections private constructor() {
  public companion object {
    public fun actionToChildMode(): NavDirections =
        ActionOnlyNavDirections(R.id.action_to_child_mode)

    public fun actionToParentMode(): NavDirections =
        ActionOnlyNavDirections(R.id.action_to_parent_mode)
  }
}
