package com.ekades.temandoa.features.quranlist.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.temandoa.R
import com.ekades.temandoa.features.quranlist.model.Surah
import com.ekades.temandoa.lib.core.ui.foundation.background.CornerBackgroundFullRounded
import com.ekades.temandoa.lib.core.ui.foundation.color.ColorPalette
import com.ekades.temandoa.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing
import com.ekades.temandoa.lib.ui.asset.extension.dp
import com.ekades.temandoa.lib.ui.component.misc.DividerCV
import kotlinx.android.synthetic.main.cv_quran_surah_list_item.view.*

class QuranListItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<QuranListItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var surah: Surah? = null
        var onItemClickListener: ((Surah) -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_quran_surah_list_item, this)
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
                tvSurahNameAr.text = nama
                tvSurahMean.text = arti
                tvType.text = showType
                tvTotalAyah.text = "$jumlahAyat ayat"
                setOnClickListener {
                    onItemClickListener?.invoke(this)
                }
            }
        }
    }
}