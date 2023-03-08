package com.ekades.movieapps.lib.ui.component.extension

import android.text.InputFilter
import androidx.appcompat.widget.AppCompatTextView

fun AppCompatTextView.maxLength(max: Int) {
    this.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max))
}