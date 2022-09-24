package com.ekades.poststest.lib.ui.component.base

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekades.poststest.lib.core.ui.extension.tintDrawable
import com.ekades.poststest.lib.core.ui.foundation.color.ColorPalette
import com.ekades.poststest.lib.ui.asset.attributes.IconSize
import com.ekades.poststest.lib.ui.asset.extension.asColor
import com.ekades.poststest.lib.ui.asset.extension.dp
import com.ekades.poststest.lib.ui.component.R
import com.ekades.temandoa.lib.ui.component.extension.setSize

open class ImageHolder internal constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attributeSet, defStyle) {

    private val state: State = State()

    init {
        setPadding(2.dp())

        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.ImageHolder,
            0,
            0
        )

        state.apply {
            imageDrawable = typedArray.getResourceId(R.styleable.ImageHolder_icon, -1)
            imageUrl = typedArray.getString(R.styleable.ImageHolder_iconUrl)
            imageSize = typedArray.getInt(R.styleable.ImageHolder_iconHolderSize, 1).toIconSize() ?: IconSize.MEDIUM
            typedArray.getResourceId(R.styleable.ImageHolder_iconColorTint, -1).takeIf {
                it != -1
            }?.let {
                imageTint = it.asColor()
            }

            typedArray.recycle()

            if (imageDrawable != -1 || imageTint != -1 || imageUrl.isNullOrBlank().not()) {
                attachToView(this)
            }
        }
    }

    /**
     * Bind [ImageHolder] with [State] programmatically
     * @param bind [State]
     */
    fun bind(bind: State.() -> Unit) {
        state.bind()
        attachToView(state)
    }

    private fun attachToView(state: State) {
        with(state) {
            this@ImageHolder.setSize(imageSize.value)

            if (imageDrawable != null && imageDrawable != -1) {
                val icon = ContextCompat.getDrawable(context, imageDrawable!!)

                imageTint?.takeIf {
                    hasTint(it) || it == ColorPalette.WHITE
                }?.let { tintDrawable ->
                    icon.tintDrawable(tintDrawable)
                }

                Glide.with(context)
                    .load(icon)
                    .apply(RequestOptions().override(imageSize.value, imageSize.value))
                    .into(this@ImageHolder)
            }

            if (imageUrl.isNullOrBlank().not()) {
                Glide.with(context)
                    .load(imageUrl)
                    .into(this@ImageHolder)
                if (hasTint(imageTint)) {
                    this@ImageHolder.setColorFilter(ContextCompat.getColor(context, imageTint!!))
                }
            }
        }
    }

    private fun hasTint(tint: Int?): Boolean = tint != null && tint != -1

    private fun Int.toIconSize(): IconSize? = IconSize.values().find { it.identifier == this }

    class State {
        /**
         * Icon as [DrawableRes]
         */
        @DrawableRes
        var imageDrawable: Int? = null

        /**
         * Icon from URL [String]
         */
        var imageUrl: String? = null

        /**
         * Icon Size
         */
        var imageSize: IconSize = IconSize.MEDIUM

        /**
         * Icon Tint Color as [ColorInt]
         */
        @ColorInt
        var imageTint: Int? = null
    }
}