package com.ekades.temandoa.features.murottal.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.temandoa.R
import com.ekades.temandoa.features.quranlist.model.Surah
import com.ekades.temandoa.lib.core.ui.foundation.background.CornerBackgroundFullRounded
import com.ekades.temandoa.lib.core.ui.foundation.color.ColorPalette
import com.ekades.temandoa.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing
import com.ekades.temandoa.lib.ui.asset.attributes.IconSize
import com.ekades.temandoa.lib.ui.asset.extension.dp
import com.ekades.temandoa.lib.ui.component.misc.DividerCV
import kotlinx.android.synthetic.main.cv_murottal_list_item.view.*
import kotlinx.android.synthetic.main.cv_murottal_list_item.view.bgActionPlayer
import kotlinx.android.synthetic.main.cv_murottal_list_item.view.tvDuration
import kotlinx.android.synthetic.main.cv_murottal_list_item.view.tvSurahName
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

class MurottalListItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<MurottalListItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var surah: Surah? = null
        var onItemClickListener: ((Surah) -> Unit)? = null
        var onClickPlayListener: (() -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_murottal_list_item, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        dividerCV.bind {
            dividerStyle = DividerCV.DividerStyle.STRAIGHT
        }
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            surah?.apply {
                bgNumber.background = CornerBackgroundFullRounded(Spacing.x32.value).apply {
                    setColor(ColorPalette.WHITE)
                    setStroke(1.dp(), ColorPalette.BRAND)
                }
                tvNumber.text = nomor
                tvSurahName.text = namaLatin
                tvSurahMean.text = arti
                tvTotalAyah.text = "$jumlahAyat ayat"
                tvDuration.text = nama
                setOnClickListener {
                    onItemClickListener?.invoke(this)
                }
                icPlayer.setOnClickListener {
                    if (isSelected) {
                        onClickPlayListener?.invoke()
                    } else {
                        onItemClickListener?.invoke(this)
                    }
                }
                updateColor(isSelected, isPlaying)
            }
        }

    }

    private fun updateColor(isSelected: Boolean, isPlaying: Boolean) {
        if (isSelected) {
            backgroundColor = ColorPalette.BRAND
            tvSurahName.textColor = ColorPalette.WHITE
            tvSurahMean.textColor = ColorPalette.WHITE
            tvTotalAyah.textColor = ColorPalette.WHITE
            tvDuration.textColor = ColorPalette.WHITE
            renderActionPlayer(isPlaying, ColorPalette.WHITE)
        } else {
            backgroundColor = ColorPalette.WHITE
            tvSurahName.textColor = ColorPalette.TUNDORA
            tvSurahMean.textColor = ColorPalette.TUNDORA
            tvTotalAyah.textColor = ColorPalette.TUNDORA
            tvDuration.textColor = ColorPalette.TUNDORA
            renderActionPlayer(isPlaying, ColorPalette.BRAND)
        }
    }

    private fun renderActionPlayer(isPlaying: Boolean, iconColor: Int) {
        bgActionPlayer.background = CornerBackgroundFullRounded(Spacing.x36.value).apply {
            setStroke(2.dp(), iconColor)
        }

        icPlayer.bind {
            imageDrawable = if (isPlaying) {
                R.drawable.ic_pause
            } else {
                R.drawable.ic_play
            }
            imageSize = IconSize.SMALL
            imageTint = iconColor
        }
    }
}