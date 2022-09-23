package com.ekades.fcmpushnotification.lib.core.ui.foundation.background

import android.graphics.drawable.GradientDrawable
import com.ekades.fcmpushnotification.lib.core.ui.foundation.corner.CornerRadius

abstract class DynamicCornerBackground(
    topLeftCorner: CornerRadius,
    topRightCorner: CornerRadius,
    bottomRightCorner: CornerRadius,
    bottomLeftCorner: CornerRadius
) : GradientDrawable() {
    
    init {
        cornerRadii = floatArrayOf(
            topLeftCorner.value.toFloat(),
            topLeftCorner.value.toFloat(),
            topRightCorner.value.toFloat(),
            topRightCorner.value.toFloat(),
            bottomRightCorner.value.toFloat(),
            bottomRightCorner.value.toFloat(),
            bottomLeftCorner.value.toFloat(),
            bottomLeftCorner.value.toFloat()
        )
    }
}