package com.ekades.temandoa.lib.core.ui.foundation.background

import android.graphics.drawable.GradientDrawable
import com.ekades.temandoa.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing

abstract class BaseCornerBackground internal constructor(val size: Int): GradientDrawable() {

    init {
        cornerRadius = this.size.toFloat()
    }

    internal constructor(corner: CornerRadius) : this(corner.value)

    internal constructor(spacing: Spacing) : this(spacing.value)
}