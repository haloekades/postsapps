package com.ekades.poststest.lib.ui.component.misc

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.ekades.poststest.lib.core.ui.foundation.color.ColorPalette
import com.ekades.poststest.lib.core.ui.foundation.component.setComponentMargin
import com.ekades.poststest.lib.core.ui.foundation.component.setComponentPadding
import com.ekades.poststest.lib.core.ui.foundation.container.LinearContainer
import com.ekades.poststest.lib.ui.asset.extension.dp
import com.ekades.poststest.lib.ui.component.base.ComponentState

class DividerCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearContainer<DividerCV.State>(context, attributeSet, defStyle) {

    val divider: View = View(context)

    init {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        addView(divider)
    }

    override fun render(state: State) {
        with(state) {
            setComponentMargin(componentMargin)
            setComponentPadding(componentPadding)

            divider.apply {
                layoutParams = LayoutParams(MATCH_PARENT, 1.dp())
                setBackgroundColor(ColorPalette.MERCURY)
            }
        }
    }

    enum class DividerStyle {
        STRAIGHT
    }

    override fun initState() = State()

    class State : ComponentState() {
        /**
         * Divider style as [DividerStyle], default is [DividerStyle.STRAIGHT]
         * @see [DividerStyle]
         */
        var dividerStyle: DividerStyle = DividerStyle.STRAIGHT
    }
}