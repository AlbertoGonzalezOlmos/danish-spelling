package com.example.danishspelling.presentation.child

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Boolean
import kotlin.Long
import kotlin.jvm.JvmStatic

public data class PracticeFragmentArgs(
  public val listId: Long,
  public val randomOrder: Boolean = false,
  public val incorrectOnly: Boolean = false,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putLong("listId", this.listId)
    result.putBoolean("randomOrder", this.randomOrder)
    result.putBoolean("incorrectOnly", this.incorrectOnly)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("listId", this.listId)
    result.set("randomOrder", this.randomOrder)
    result.set("incorrectOnly", this.incorrectOnly)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): PracticeFragmentArgs {
      bundle.setClassLoader(PracticeFragmentArgs::class.java.classLoader)
      val __listId : Long
      if (bundle.containsKey("listId")) {
        __listId = bundle.getLong("listId")
      } else {
        throw IllegalArgumentException("Required argument \"listId\" is missing and does not have an android:defaultValue")
      }
      val __randomOrder : Boolean
      if (bundle.containsKey("randomOrder")) {
        __randomOrder = bundle.getBoolean("randomOrder")
      } else {
        __randomOrder = false
      }
      val __incorrectOnly : Boolean
      if (bundle.containsKey("incorrectOnly")) {
        __incorrectOnly = bundle.getBoolean("incorrectOnly")
      } else {
        __incorrectOnly = false
      }
      return PracticeFragmentArgs(__listId, __randomOrder, __incorrectOnly)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): PracticeFragmentArgs {
      val __listId : Long?
      if (savedStateHandle.contains("listId")) {
        __listId = savedStateHandle["listId"]
        if (__listId == null) {
          throw IllegalArgumentException("Argument \"listId\" of type long does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"listId\" is missing and does not have an android:defaultValue")
      }
      val __randomOrder : Boolean?
      if (savedStateHandle.contains("randomOrder")) {
        __randomOrder = savedStateHandle["randomOrder"]
        if (__randomOrder == null) {
          throw IllegalArgumentException("Argument \"randomOrder\" of type boolean does not support null values")
        }
      } else {
        __randomOrder = false
      }
      val __incorrectOnly : Boolean?
      if (savedStateHandle.contains("incorrectOnly")) {
        __incorrectOnly = savedStateHandle["incorrectOnly"]
        if (__incorrectOnly == null) {
          throw IllegalArgumentException("Argument \"incorrectOnly\" of type boolean does not support null values")
        }
      } else {
        __incorrectOnly = false
      }
      return PracticeFragmentArgs(__listId, __randomOrder, __incorrectOnly)
    }
  }
}
