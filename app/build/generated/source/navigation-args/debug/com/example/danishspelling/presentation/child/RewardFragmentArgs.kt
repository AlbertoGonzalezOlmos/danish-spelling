package com.example.danishspelling.presentation.child

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Float
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class RewardFragmentArgs(
  public val totalStars: Int,
  public val accuracy: Float,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("totalStars", this.totalStars)
    result.putFloat("accuracy", this.accuracy)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("totalStars", this.totalStars)
    result.set("accuracy", this.accuracy)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): RewardFragmentArgs {
      bundle.setClassLoader(RewardFragmentArgs::class.java.classLoader)
      val __totalStars : Int
      if (bundle.containsKey("totalStars")) {
        __totalStars = bundle.getInt("totalStars")
      } else {
        throw IllegalArgumentException("Required argument \"totalStars\" is missing and does not have an android:defaultValue")
      }
      val __accuracy : Float
      if (bundle.containsKey("accuracy")) {
        __accuracy = bundle.getFloat("accuracy")
      } else {
        throw IllegalArgumentException("Required argument \"accuracy\" is missing and does not have an android:defaultValue")
      }
      return RewardFragmentArgs(__totalStars, __accuracy)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): RewardFragmentArgs {
      val __totalStars : Int?
      if (savedStateHandle.contains("totalStars")) {
        __totalStars = savedStateHandle["totalStars"]
        if (__totalStars == null) {
          throw IllegalArgumentException("Argument \"totalStars\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"totalStars\" is missing and does not have an android:defaultValue")
      }
      val __accuracy : Float?
      if (savedStateHandle.contains("accuracy")) {
        __accuracy = savedStateHandle["accuracy"]
        if (__accuracy == null) {
          throw IllegalArgumentException("Argument \"accuracy\" of type float does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"accuracy\" is missing and does not have an android:defaultValue")
      }
      return RewardFragmentArgs(__totalStars, __accuracy)
    }
  }
}
