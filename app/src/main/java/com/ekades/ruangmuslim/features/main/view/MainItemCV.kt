package com.ekades.ruangmuslim.features.main.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.ruangmuslim.R
import com.ekades.ruangmuslim.features.main.model.MainSection
import com.ekades.ruangmuslim.lib.core.ui.foundation.background.shadowLarge
import com.ekades.ruangmuslim.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.ruangmuslim.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.ruangmuslim.lib.core.ui.foundation.spacing.Spacing
import com.ekades.ruangmuslim.lib.ui.asset.attributes.IconSize
import kotlinx.android.synthetic.main.cv_main_item.view.*

class MainItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<MainItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var section: MainSection? = null
        var onItemClickListener: ((MainSection) -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_main_item, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setContainerMargin(
            left = Spacing.x8,
            right = Spacing.x8,
            top = Spacing.x12,
            bottom = Spacing.x4
        )
        setContainerPadding(vertical = Spacing.x24, horizontal = Spacing.x16)
        shadowLarge(CornerRadius.XLARGE)
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            section?.apply {
                titleTextView.text = name
                iconCV.bind {
                    imageSize = IconSize.LARGE
                    imageDrawable = icon
                }

                setOnClickListener {
                    onItemClickListener?.invoke(this)
                }
            }
        }
    }
}