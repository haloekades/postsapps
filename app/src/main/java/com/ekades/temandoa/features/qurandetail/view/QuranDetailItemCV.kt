package com.ekades.temandoa.features.qurandetail.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.temandoa.R
import com.ekades.temandoa.features.qurandetail.model.SurahDetail
import com.ekades.temandoa.lib.core.ui.foundation.background.CornerBackgroundFullRounded
import com.ekades.temandoa.lib.core.ui.foundation.color.ColorPalette
import com.ekades.temandoa.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing
import com.ekades.temandoa.lib.ui.asset.extension.dp
import com.ekades.temandoa.lib.ui.component.misc.DividerCV
import kotlinx.android.synthetic.main.cv_quran_surah_detail_item.view.*
import org.jetbrains.anko.backgroundColor

class QuranDetailItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<QuranDetailItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var surahDetail: SurahDetail? = null
        var bgColor: Int = ColorPalette.WHITE
    }

    init {
        View.inflate(context, R.layout.cv_quran_surah_detail_item, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        dividerCV.bind {
            dividerStyle = DividerCV.DividerStyle.STRAIGHT
        }
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            surahDetail?.apply {
                tvNumber.text = nomor
                tvArabic.text = ar
                tvAyahMean.text = id
            }
            backgroundColor = bgColor

        }
    }
}