package com.ekades.movieapps.lib.core.ui.foundation.background
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import androidx.annotation.ColorInt
import com.ekades.movieapps.lib.core.ui.R
import com.ekades.movieapps.lib.core.ui.foundation.color.ColorPalette
import com.ekades.movieapps.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.movieapps.lib.ui.asset.extension.asColor
import com.ekades.movieapps.lib.ui.asset.extension.dp

/**
 * Apply Small Shadow to [View]
 * @param cornerRadius [CornerRadius]
 * @param backgroundColor as [ColorInt], use [ColorPalette]
 */
fun View.shadowSmall(
    cornerRadius: CornerRadius = CornerRadius.NONE,
    @ColorInt backgroundColor: Int = ColorPalette.WHITE
) {
    val cornerRadiusValue: Float = cornerRadius.value.toFloat()
    val outerRadius = FloatArray(8) { cornerRadiusValue }
    val layer1 = ShapeDrawable().apply {
        paint.color = R.color.layer1_background_small.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(1.dp(), 1.dp(), 1.dp(), 1.dp())
    }
    val layer2 = ShapeDrawable().apply {
        paint.color = R.color.layer2_background_small.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val backgroundLayer = ShapeDrawable().apply {
        paint.color = backgroundColor
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    this.background = LayerDrawable(arrayOf<Drawable>(layer1, layer2, backgroundLayer))
}

/**
 * Apply Small Reverse Shadow to [View]
 * @param cornerRadius [CornerRadius]
 * @param backgroundColor as [ColorInt], use [ColorPalette]
 */
fun View.shadowSmallReverse(
    cornerRadius: CornerRadius = CornerRadius.NONE,
    @ColorInt backgroundColor: Int = ColorPalette.WHITE
) {
    val cornerRadiusValue: Float = cornerRadius.value.toFloat()
    val outerRadius = FloatArray(8) { cornerRadiusValue }
    val layer1 = ShapeDrawable().apply {
        paint.color = R.color.layer1_background_small.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(1.dp(), 1.dp(), 1.dp(), 1.dp())
    }
    val layer2 = ShapeDrawable().apply {
        paint.color = R.color.layer2_background_small.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 1.dp(), 0, 0)
    }
    val backgroundLayer = ShapeDrawable().apply {
        paint.color = backgroundColor
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    this.background = LayerDrawable(arrayOf<Drawable>(layer1, layer2, backgroundLayer))
}

/**
 * Apply Medium Shadow to [View]
 * @param cornerRadius [CornerRadius]
 * @param backgroundColor as [ColorInt], use [ColorPalette]
 */
fun View.shadowMedium(
    cornerRadius: CornerRadius = CornerRadius.NONE,
    @ColorInt backgroundColor: Int = ColorPalette.WHITE
) {
    val cornerRadiusValue: Float = cornerRadius.value.toFloat()
    val outerRadius = FloatArray(8) { cornerRadiusValue }
    val layer1 = ShapeDrawable().apply {
        paint.color = R.color.layer1_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(1.dp(), 1.dp(), 1.dp(), 1.dp())
    }
    val layer2 = ShapeDrawable().apply {
        paint.color = R.color.layer2_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val layer3 = ShapeDrawable().apply {
        paint.color = R.color.layer3_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(1, 1, 1, 1.dp())
    }
    val layer4 = ShapeDrawable().apply {
        paint.color = R.color.layer4_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val layer5 = ShapeDrawable().apply {
        paint.color = R.color.layer5_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val layer6 = ShapeDrawable().apply {
        paint.color = R.color.layer6_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val layer7= ShapeDrawable().apply {
        paint.color = R.color.layer7_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val layer8 = ShapeDrawable().apply {
        paint.color = R.color.layer8_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(1.dp(), 1.dp(), 1.dp(), 1.dp())
    }
    val backgroundLayer = ShapeDrawable().apply {
        paint.color = backgroundColor
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    this.background = LayerDrawable(arrayOf<Drawable>(layer1, layer2, layer3, layer4, layer5, layer6, layer7, layer8, backgroundLayer))
}

/**
 * Apply Large Shadow to [View]
 * @param cornerRadius [CornerRadius]
 * @param backgroundColor as [ColorInt], use [ColorPalette]
 */
fun View.shadowLarge(
    cornerRadius: CornerRadius = CornerRadius.NONE,
    @ColorInt backgroundColor: Int = ColorPalette.WHITE
) {
    val cornerRadiusValue: Float = cornerRadius.value.toFloat()
    val outerRadius = FloatArray(8) { cornerRadiusValue }
    val layer1 = ShapeDrawable().apply {
        paint.color = R.color.layer1_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val layer2 = ShapeDrawable().apply {
        paint.color = R.color.layer2_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(1.dp(), 1.dp(), 1.dp(), 1.dp())
    }
    val layer3 = ShapeDrawable().apply {
        paint.color = R.color.layer3_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val layer4 = ShapeDrawable().apply {
        paint.color = R.color.layer4_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(1.dp(), 1.dp(), 1.dp(), 1.dp())
    }
    val layer5 = ShapeDrawable().apply {
        paint.color = R.color.layer5_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val layer6 = ShapeDrawable().apply {
        paint.color = R.color.layer6_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(1.dp(), 0, 1.dp(), 1.dp())
    }
    val layer7 = ShapeDrawable().apply {
        paint.color = R.color.layer7_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(0, 0, 0, 1.dp())
    }
    val layer8 = ShapeDrawable().apply {
        paint.color = R.color.layer8_background.asColor()
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(1.dp(), 0, 1.dp(), 1.dp())
    }
    val backgroundLayer = ShapeDrawable().apply {
        paint.color = backgroundColor
        shape = RoundRectShape(outerRadius, null, null)
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    this.background = LayerDrawable(
        arrayOf<Drawable>(
            layer1,
            layer2,
            layer3,
            layer4,
            layer5,
            layer6,
            layer7,
            layer8,
            backgroundLayer
        )
    )
}