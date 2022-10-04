package com.ekades.temandoa.lib.ui.component.extension

import androidx.appcompat.widget.AppCompatImageView

/**
 * Set [AppCompatImageView] size with [Int]
 * @param size as [Int]
 */
fun AppCompatImageView.setSize(size: Int) {
    this.apply {
        minimumWidth = size
        minimumHeight = size
        maxWidth = size
        maxHeight = size
    }
}

/**
 * Set [AppCompatImageView] size with [Int]
 * @param size as [Int]
 */
fun AppCompatImageView.setSizeWithLayoutParams(size: Int) {
    this.layoutParams?.run {
        width = size
        height = size
    }
}