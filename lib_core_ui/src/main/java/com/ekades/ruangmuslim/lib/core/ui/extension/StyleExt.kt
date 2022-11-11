package com.ekades.ruangmuslim.lib.core.ui.extension

import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StyleRes
import com.airbnb.paris.extensions.style

/**
 * Set [TextView] Style With [StyleRes] from [Typography]
 * @param styleResId defined from [Typography]
 */
fun TextView.setTypography(@StyleRes styleResId: Int) {
    this.style(styleResId)
}

/**
 * Set [EditText] Style With [StyleRes]
 * @param styleResId defined from [Typography]
 */
fun EditText.setTypography(@StyleRes styleResId: Int) {
    this.style(styleResId)
}