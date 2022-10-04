package com.ekades.temandoa.lib.ui.component.extension

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable

object ComponentUtils {
    val default get() = intArrayOf()
    val enabled get() = intArrayOf(android.R.attr.state_enabled)
    val disabled get() = intArrayOf(-android.R.attr.state_enabled)
    val selected get() = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected)
    val pressedEnabled get() = intArrayOf(
        android.R.attr.state_pressed,
        android.R.attr.state_enabled
    )
    val focusedEnabled get() = intArrayOf(
        android.R.attr.state_focused,
        android.R.attr.state_enabled
    )
    val unpressedEnabled get() = intArrayOf(
        -android.R.attr.state_pressed,
        android.R.attr.state_enabled
    )
    val unpressedDisabled get() = intArrayOf(
        -android.R.attr.state_pressed,
        -android.R.attr.state_enabled
    )
    val checked get() = intArrayOf(android.R.attr.state_checked)
    val unchecked get() = intArrayOf(-android.R.attr.state_checked)
    val checkedEnabled get() = intArrayOf(
        android.R.attr.state_checked,
        android.R.attr.state_enabled
    )
    val checkedDisabled get() = intArrayOf(
        android.R.attr.state_checked,
        -android.R.attr.state_enabled
    )
    val uncheckedEnabled get() = intArrayOf(
        -android.R.attr.state_checked,
        android.R.attr.state_enabled
    )
    val uncheckedDisabled get() = intArrayOf(
        -android.R.attr.state_checked,
        -android.R.attr.state_enabled
    )

    fun colorStateList(vararg colorStates: Pair<IntArray, Int>): ColorStateList {
        return ColorStateList(
            colorStates.map { it.first }.toTypedArray(),
            colorStates.map { it.second }.toIntArray()
        )
    }

    fun drawableStateList(vararg drawableStates: Pair<IntArray, Drawable?>): StateListDrawable {
        val stateListDrawable = StateListDrawable()
        drawableStates.forEach {
            stateListDrawable.addState(it.first, it.second)
        }
        return stateListDrawable
    }
}