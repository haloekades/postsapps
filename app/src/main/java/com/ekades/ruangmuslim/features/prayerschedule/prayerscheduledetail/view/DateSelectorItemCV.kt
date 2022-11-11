package com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.ruangmuslim.R
import com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.model.DateSelector
import com.ekades.ruangmuslim.lib.core.ui.extension.setViewMargin
import com.ekades.ruangmuslim.lib.core.ui.foundation.background.CornerBackgroundLarge
import com.ekades.ruangmuslim.lib.core.ui.foundation.color.ColorPalette
import com.ekades.ruangmuslim.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.ruangmuslim.lib.core.ui.foundation.spacing.Spacing
import com.ekades.ruangmuslim.lib.ui.asset.extension.dp
import kotlinx.android.synthetic.main.cv_date_selector.view.*

class DateSelectorItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<DateSelectorItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var dateSelector: DateSelector? = null
        var onSelectedDateListener: ((String) -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_date_selector, this)
        setContainerParams(Spacing.x76.value, LayoutParams.WRAP_CONTENT)
        setViewMargin(
            left = Spacing.x8.value,
            right = Spacing.x8.value
        )
        setPadding(Spacing.x12.value, Spacing.x4.value, Spacing.x12.value, Spacing.x8.value)
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            dateSelector?.apply {
                tvDay.text = day
                tvDate.text = date
                background = CornerBackgroundLarge().apply {
                    if (isSelected) {
                        setColor(ColorPalette.NARVIK)
                        setStroke(1.dp(), ColorPalette.BRAND)
                    } else {
                        setColor(ColorPalette.WHITE)
                        setStroke(1.dp(), ColorPalette.BRAND)
                    }
                }
                setOnClickListener {
                    onSelectedDateListener?.invoke(date)
                }
            }
        }
    }
}