package com.example.danishspelling.presentation.parent

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Long
import kotlin.jvm.JvmStatic

public data class SentenceManagementFragmentArgs(
  public val listId: Long,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putLong("listId", this.listId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("listId", this.listId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): SentenceManagementFragmentArgs {
      bundle.setClassLoader(SentenceManagementFragmentArgs::class.java.classLoader)
      val __listId : Long
      if (bundle.containsKey("listId")) {
        __listId = bundle.getLong("listId")
      } else {
        throw IllegalArgumentException("Required argument \"listId\" is missing and does not have an android:defaultValue")
      }
      return SentenceManagementFragmentArgs(__listId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        SentenceManagementFragmentArgs {
      val __listId : Long?
      if (savedStateHandle.contains("listId")) {
        __listId = savedStateHandle["listId"]
        if (__listId == null) {
          throw IllegalArgumentException("Argument \"listId\" of type long does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"listId\" is missing and does not have an android:defaultValue")
      }
      return SentenceManagementFragmentArgs(__listId)
    }
  }
}
