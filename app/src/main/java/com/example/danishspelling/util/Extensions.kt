package com.example.danishspelling.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * Extension functions for common Android operations
 */

// View extensions
fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.enable() {
    isEnabled = true
    alpha = 1.0f
}

fun View.disable() {
    isEnabled = false
    alpha = 0.5f
}

// Keyboard extensions
fun View.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

// Toast extensions
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

// Float extensions for percentages
fun Float.toPercentageString(): String {
    return String.format("%.0f%%", this)
}

// Star count to emoji
fun Int.toStarEmoji(): String {
    return "⭐".repeat(this)
}
