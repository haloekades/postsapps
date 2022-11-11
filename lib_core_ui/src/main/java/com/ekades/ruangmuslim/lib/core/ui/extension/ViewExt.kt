package com.ekades.ruangmuslim.lib.core.ui.extension

import android.view.View
import android.view.ViewGroup
import com.ekades.ruangmuslim.lib.core.ui.foundation.spacing.Spacing

/**
 * Set [View] to View.VISIBLE
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

/**
 * Set [View] to View.INVISIBLE
 */
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Set [View] to View.GONE
 */
fun View.gone() {
    this.visibility = View.GONE
}

/**
 * Enable [View]
 */
fun View.enable() {
    this.isEnabled = true
}

/**
 * Disable [View]
 */
fun View.disable() {
    this.isEnabled = false
}

/**
 * Show [View] with condition
 * @param condition as [Boolean]
 */
fun View.showIf(condition: () -> Boolean) {
    if (this.visibility != View.VISIBLE && condition()) {
        this.visibility = View.VISIBLE
    }
}

/**
 * Hide [View] with condition
 * @param condition as [Boolean]
 */
fun View.hideIf(block: () -> Boolean) {
    if (this.visibility != View.INVISIBLE && block()) {
        this.visibility = View.INVISIBLE
    }
}

/**
 * Remove [View] from container with condition
 * @param condition as [Boolean]
 */
fun View.removeIf(block: () -> Boolean) {
    if (this.visibility != View.GONE && block()) {
        this.visibility = View.GONE
    }
}

/**
 * Set [View] padding with [Int]
 * @param left left padding
 * @param top top padding
 * @param right right padding
 * @param bottom bottom padding
 */
fun View.setViewPadding(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    setPadding(
        left ?: paddingLeft,
        top ?: paddingTop,
        right ?: paddingRight,
        bottom ?: paddingBottom
    )
}

/**
 * Set [View] padding with [Spacing]
 * @param all padding to all sides
 */
fun View.setViewPadding(all: Spacing) {
    setViewPadding(all, all, all, all)
}

/**
 * Set [View] padding with [Spacing]
 * @param horizontal left and right padding
 * @param vertical top and bottom padding
 */
fun View.setViewPadding(horizontal: Spacing, vertical: Spacing) {
    setViewPadding(horizontal, vertical, horizontal, vertical)
}

/**
 * Set [View] padding with [Spacing]
 * @param left left padding
 * @param top top padding
 * @param right right padding
 * @param bottom bottom padding
 */
fun View.setViewPadding(
    left: Spacing? = null,
    top: Spacing? = null,
    right: Spacing? = null,
    bottom: Spacing? = null
) {
    setPadding(
        left?.value ?: paddingLeft,
        top?.value ?: paddingTop,
        right?.value ?: paddingRight,
        bottom?.value ?: paddingBottom
    )
}

/**
 * Set [View] margin with [Spacing]
 * @param all margin to all sides
 */
fun View.setViewMargin(all: Spacing) {
    setViewMargin(all, all, all, all)
}

/**
 * Set [View] margin with [Spacing]
 * @param horizontal left and right margin
 * @param vertical top and bottom margin
 */
fun View.setViewMargin(horizontal: Spacing, vertical: Spacing) {
    setViewMargin(horizontal, vertical, horizontal, vertical)
}

/**
 * Set [View] margin with [Spacing]
 * @param left left margin
 * @param top top margin
 * @param right right margin
 * @param bottom bottom margin
 */
fun View.setViewMargin(
    left: Spacing? = null,
    top: Spacing? = null,
    right: Spacing? = null,
    bottom: Spacing? = null
) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.setMargins(
            left?.value ?: paddingLeft,
            top?.value ?: paddingTop,
            right?.value ?: paddingRight,
            bottom?.value ?: paddingBottom
        )
    }
}

/**
 * Set [View] margin with [Int]
 * @param left left margin
 * @param top top margin
 * @param right right margin
 * @param bottom bottom margin
 */
fun View.setViewMargin(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.setMargins(
            left ?: paddingLeft,
            top ?: paddingTop,
            right ?: paddingRight,
            bottom ?: paddingBottom
        )
    }
}