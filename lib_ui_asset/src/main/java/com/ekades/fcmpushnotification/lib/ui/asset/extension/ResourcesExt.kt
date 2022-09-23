package com.ekades.fcmpushnotification.lib.ui.asset.extension

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.ekades.fcmpushnotification.lib.application.ApplicationProvider
import kotlin.math.roundToInt

/**
 * Convert Int [Resources] into [ColorInt]
 */
@ColorInt
fun Int.asColor(): Int {
    return try {
        ContextCompat.getColor(ApplicationProvider.context, this)
    } catch (e: Resources.NotFoundException) {
        ApplicationProvider.forceCrash(e.message)
        this
    }
}

/**
 * Convert [Float] into Density Independent Pixel
 */
fun Float.dp(): Int = (this * (ApplicationProvider.context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()

/**
 * Convert [Int] into Density Independent Pixel
 */
fun Int.dp() = this.toFloat().dp()

/**
 * Convert [Resources] into Density Independent Pixel
 */
fun Int.asDimen(): Int {
    return try {
        ApplicationProvider.context.resources.getDimensionPixelSize(this)
    } catch (e: Resources.NotFoundException) {
        ApplicationProvider.forceCrash(e.message)
        this
    }
}
