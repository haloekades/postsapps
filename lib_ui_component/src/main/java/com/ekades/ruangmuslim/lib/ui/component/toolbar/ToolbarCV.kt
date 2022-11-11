package com.ekades.ruangmuslim.lib.ui.component.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.ekades.ruangmuslim.lib.core.ui.foundation.color.ColorPalette
import com.ekades.ruangmuslim.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.ruangmuslim.lib.ui.asset.attributes.IconSize
import com.ekades.ruangmuslim.lib.ui.asset.icon.BasicIcon
import com.ekades.ruangmuslim.lib.ui.component.R
import kotlinx.android.synthetic.main.cv_toolbar.view.*

class ToolbarCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<ToolbarCV.State>(context, attributeSet, defStyle) {

    class State {
        var toolbarTitle: String? = null
        var onClickBackListener: (() -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_toolbar, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setBackgroundColor(ColorPalette.BRAND)
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            if (toolbarTitle.isNullOrBlank()) {
                toolbarTextView.isInvisible = true
            } else {
                toolbarTextView.text = toolbarTitle
                toolbarTextView.isVisible = true
            }

            if (onClickBackListener != null) {
                backIconCV.apply {
                    isVisible = true
                    bind {
                        imageSize = IconSize.SMALL
                        imageDrawable = BasicIcon.CHEVRON_RIGHT_WHITE
                    }
                    rotation = 180f
                    backIconCV.setOnClickListener {
                        onClickBackListener?.invoke()
                    }
                }
            } else {
                backIconCV.isVisible = false
            }
        }
    }
}