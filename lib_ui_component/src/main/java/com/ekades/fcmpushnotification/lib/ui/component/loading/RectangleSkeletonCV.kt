package com.ekades.fcmpushnotification.lib.ui.component.loading

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.VisibleForTesting
import com.facebook.shimmer.ShimmerFrameLayout
import com.ekades.fcmpushnotification.lib.core.ui.foundation.color.ColorPalette
import com.ekades.fcmpushnotification.lib.core.ui.foundation.component.setComponentMargin
import com.ekades.fcmpushnotification.lib.core.ui.foundation.component.setComponentPadding
import com.ekades.fcmpushnotification.lib.core.ui.foundation.container.LinearContainer
import com.ekades.fcmpushnotification.lib.ui.component.R
import com.ekades.fcmpushnotification.lib.ui.component.base.ComponentState

class RectangleSkeletonCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearContainer<RectangleSkeletonCV.State>(context, attributeSet, defStyle) {
    override fun initState(): State = State()

    @VisibleForTesting
    internal val shimmerLayout = ShimmerFrameLayout(context).also {
        it.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    init {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        addView(shimmerLayout)
    }

    override fun render(state: State) {
        gravity = state.gravity

        with(shimmerLayout) {
            setComponentPadding(state.componentPadding)
            setComponentMargin(state.componentMargin)

            val skeleton = View(context).apply {
                id = R.id.rectSkeletonCV
                setBackgroundColor(ColorPalette.ALTO)
                layoutParams = MarginLayoutParams(state.width, state.height)
            }

            removeAllViews()
            addView(skeleton)

            startShimmer()
        }
    }

    override fun unbind() {
        super.unbind()
        shimmerLayout.stopShimmer()
    }

    class State : ComponentState() {
        /**
         * Skeleton Rectangle Height as [Int], default is [MATCH_PARENT]
         */
        var height: Int = MATCH_PARENT

        /**
         * Skeleton Rectangle Width as [Int], default is [MATCH_PARENT]
         */
        var width: Int = MATCH_PARENT

        /**
         * Skeleton Gravity Alignment, default is [Gravity.CENTER]
         */
        var gravity: Int = Gravity.CENTER
    }
}