package com.ekades.ruangmuslim.features.qurandetail.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.ruangmuslim.R
import com.ekades.ruangmuslim.features.qurandetail.model.SurahDetail
import com.ekades.ruangmuslim.lib.core.ui.foundation.background.CornerBackgroundFullRounded
import com.ekades.ruangmuslim.lib.core.ui.foundation.color.ColorPalette
import com.ekades.ruangmuslim.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.ruangmuslim.lib.core.ui.foundation.spacing.Spacing
import com.ekades.ruangmuslim.lib.ui.asset.extension.dp
import kotlinx.android.synthetic.main.cv_quran_surah_detail_item.view.*
import kotlinx.android.synthetic.main.cv_quran_surah_detail_item.view.bgNumber
import kotlinx.android.synthetic.main.cv_quran_surah_detail_item.view.tvNumber
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
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            surahDetail?.apply {
                bgNumber.background = CornerBackgroundFullRounded(Spacing.x32.value).apply {
                    setColor(ColorPalette.WHITE)
                    setStroke(1.dp(), ColorPalette.BRAND)
                }
                tvNumber.text = nomor.toString()
                tvArabic.text = ar
                tvAyahMean.text = idn
            }
            backgroundColor = bgColor
        }
    }
}