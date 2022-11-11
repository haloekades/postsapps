package com.ekades.ruangmuslim.features.prayerdetail

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.viewpager.widget.PagerAdapter
import com.ekades.ruangmuslim.R
import com.ekades.ruangmuslim.features.prayerdetail.model.PrayerItem
import com.ekades.ruangmuslim.lib.application.ApplicationProvider
import com.ekades.ruangmuslim.lib.core.ui.foundation.background.CornerBackgroundLarge
import com.ekades.ruangmuslim.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.ruangmuslim.lib.core.ui.foundation.color.ColorPalette
import com.ekades.ruangmuslim.lib.ui.component.misc.DividerCV
import com.ekades.ruangmuslim.lib.ui.component.selection.CheckBoxCV

class PrayerDetailAdapter(private val data: List<PrayerItem>?) : PagerAdapter() {
    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    override fun getCount(): Int = data?.size ?: 0

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout = LayoutInflater.from(container.context)
            .inflate(R.layout.cv_prayer_detail, container, false)

        val prayerNameTextView = layout.findViewById<TextView>(R.id.prayerNameTextView)
        val readCountTextView = layout.findViewById<TextView>(R.id.readCountTextView)
        val mainContentView = layout.findViewById<ConstraintLayout>(R.id.mainContentView)

        val arabicHeaderView = layout.findViewById<Group>(R.id.arabicHeaderView)
        val arabicHeaderTextView = layout.findViewById<TextView>(R.id.arabicHeaderTextView)
        val arabicHeaderMeaningTextView =
            layout.findViewById<TextView>(R.id.arabicHeaderMeaningTextView)

        val arabicTextView = layout.findViewById<TextView>(R.id.arabicTextView)
        val indonesianTextView = layout.findViewById<TextView>(R.id.latinTextView)

        val meanView = layout.findViewById<Group>(R.id.meanView)
        val dividerMean = layout.findViewById<DividerCV>(R.id.dividerMean)
        val bgMeanView = layout.findViewById<ConstraintLayout>(R.id.bgMeanView)
        val meanTextView = layout.findViewById<TextView>(R.id.meanValueTextView)

        val benefitsView = layout.findViewById<Group>(R.id.benefitsView)
        val dividerBenefits = layout.findViewById<DividerCV>(R.id.dividerBenefits)
        val bgBenefitsView = layout.findViewById<ConstraintLayout>(R.id.bgBenefitsView)
        val benefitsTextView = layout.findViewById<TextView>(R.id.benefitsTextView)
        val benefitsValueTextView = layout.findViewById<TextView>(R.id.benefitsValueTextView)

        val sourceView = layout.findViewById<Group>(R.id.sourceView)
        val dividerSource = layout.findViewById<DividerCV>(R.id.dividerSource)
        val sourceTextView = layout.findViewById<TextView>(R.id.sourceTextView)

        val dividerCb = layout.findViewById<DividerCV>(R.id.dividerCb)
        val cbShowLatin = layout.findViewById<CheckBoxCV>(R.id.cbShowLatin)

        cbShowLatin.bind {
            isChecked = false
            onCheckChangedListener = { checked ->
                indonesianTextView.isVisible = checked
            }
        }


        val prayer = data?.getOrNull(position)

        prayer?.apply {
            if (name.isNotBlank()) {
                prayerNameTextView.text = name
                prayerNameTextView.isVisible = true
            } else {
                prayerNameTextView.visibility = if (readCount != null) {
                    INVISIBLE
                } else {
                    GONE
                }
            }

            readCount?.apply {
                readCountTextView.text =
                    ApplicationProvider.context.getString(R.string.read_count, this.toString())
                readCountTextView.isVisible = true
            } ?: kotlin.run {
                readCountTextView.isVisible = false
            }

            if (arabicHeader?.isNotBlank() == true) {
                arabicHeaderTextView.text = arabicHeader
                arabicHeaderMeaningTextView.text = arabicHeaderMeaning
                arabicHeaderView.isVisible = true
            } else {
                arabicHeaderView.isVisible = false
            }

            arabicTextView.text = arabic
            indonesianTextView.text = latin

            mainContentView.background = CornerBackroundTopMedium().apply {
                setColor(ColorPalette.WHITE)
            }

            if (arabicMeaning.isNotBlank()) {
                meanView.isVisible = true
                bgMeanView.background = CornerBackgroundLarge().apply {
                    setColor(ColorPalette.BRAND)
                    setStroke(1, ColorPalette.BRAND)
                }
                bgMeanView.background.alpha = 45
                meanTextView.text = arabicMeaning
            } else {
                meanView.isVisible = false
            }

            if (benefits?.isNotBlank() == true || tafsir?.isNotBlank() == true) {
                benefitsView.isVisible = true
                bgBenefitsView.background = CornerBackgroundLarge().apply {
                    setColor(ColorPalette.BRAND)
                    setStroke(1, ColorPalette.BRAND)
                }
                bgBenefitsView.background.alpha = 45

                benefitsValueTextView.text = benefits ?: tafsir
                if (tafsir != null) {
                    benefitsTextView.text =
                        ApplicationProvider.context.getString(R.string.title_tafsir)
                }
            } else {
                benefitsView.isVisible = false
            }

            if (source?.isNotBlank() == true) {
                sourceView.isVisible = true
                sourceTextView.text = source

            } else {
                sourceView.isVisible = false
            }

            dividerCb.addStraightStyle()
            dividerMean.addStraightStyle()
            dividerBenefits.addStraightStyle()
            dividerSource.addStraightStyle()

        }

        container.addView(layout, 0)

        return layout
    }

    private fun DividerCV.addStraightStyle() {
        this.bind {
            dividerStyle = DividerCV.DividerStyle.STRAIGHT
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as? View)
    }
}