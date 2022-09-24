package com.ekades.poststest.lib.core.ui.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.graphics.drawable.DrawableWrapper
import androidx.core.graphics.drawable.DrawableCompat

/**
 * Add Tint to Compound Drawable
 */
fun Drawable?.tintDrawable(color: ColorStateList): Drawable? {
    return this?.apply {
        DrawableCompat.wrap(this)
        DrawableCompat.setTintList(this, color)
        DrawableCompat.setTintMode(this, PorterDuff.Mode.SRC_IN)
    }
}

/**
 * Resize Compound Drawable
 */
@SuppressLint("RestrictedApi")
fun Drawable?.resizeDrawable(context: Context, @Px width: Int, @Px height: Int): Drawable {
    this?.also {
        if (it.intrinsicWidth == width && it.intrinsicHeight == height) return it

        var d = this
        if (this is DrawableWrapper) {
            d = this.wrappedDrawable
        }

        return if (d is BitmapDrawable) {
            val resizedBitmap = Bitmap.createScaledBitmap(d.bitmap, width, height, true)
            BitmapDrawable(context.resources, resizedBitmap)
        } else {
            val bitmap = Bitmap.createBitmap(
                    if (this.intrinsicWidth > 0) this.intrinsicWidth else width,
                    if (this.intrinsicHeight > 0) this.intrinsicHeight else height,
                    Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            this.setBounds(0, 0, canvas.width, canvas.height)
            this.draw(canvas)

            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            BitmapDrawable(context.resources, resizedBitmap)
        }
    }
    return ColorDrawable()
}

/**
 * Add tint to drawable
 */
fun Drawable?.tintDrawable(@ColorInt color: Int): Drawable? {
    return this?.apply {
        DrawableCompat.wrap(this)
        DrawableCompat.setTint(this, color)
        DrawableCompat.setTintMode(this, PorterDuff.Mode.SRC_IN)
    }
}