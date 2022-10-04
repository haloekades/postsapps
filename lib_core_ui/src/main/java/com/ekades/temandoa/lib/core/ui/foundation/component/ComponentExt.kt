package com.ekades.temandoa.lib.core.ui.foundation.component

import android.view.View
import android.view.ViewGroup
import com.ekades.temandoa.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing

data class Rectangle constructor(
    val left: Spacing? = null,
    val top: Spacing? = null,
    val right: Spacing? = null,
    val bottom: Spacing? = null
) {
    constructor(all: Spacing) : this(all, all, all, all)
    
    constructor(horizontal: Spacing, vertical: Spacing): this(horizontal, vertical, horizontal, vertical)
}


/**
 * Set [View] margin with [Rectangle]
 * @param rect as [Rectangle]
 */
fun View.setComponentMargin(rect: Rectangle?) {
    rect?.let {
        val lp = this.layoutParams
        if (lp is ViewGroup.MarginLayoutParams) {
            lp.setMargins(
                rect.left?.value ?: paddingLeft,
                rect.top?.value ?: paddingTop,
                rect.right?.value ?: paddingRight,
                rect.bottom?.value ?: paddingBottom
            )
        }
    }
}

/**
 * Set [View] padding with [Rectangle]
 * @param rect as [Rectangle]
 */
fun View.setComponentPadding(rect: Rectangle?) {
    rect?.let {
        setPadding(
            rect.left?.value ?: paddingLeft,
            rect.top?.value ?: paddingTop,
            rect.right?.value ?: paddingRight,
            rect.bottom?.value ?: paddingBottom
        )
    }
}

data class CornerRect constructor(
    val left: CornerRadius? = null,
    val top: CornerRadius? = null,
    val right: CornerRadius? = null,
    val bottom: CornerRadius? = null
) {
    constructor(all: CornerRadius) : this(all, all, all, all)
    
    constructor(horizontal: CornerRadius, vertical: CornerRadius): this(horizontal, vertical, horizontal, vertical)
}
