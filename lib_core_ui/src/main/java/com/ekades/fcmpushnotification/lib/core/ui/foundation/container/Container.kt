package com.ekades.fcmpushnotification.lib.core.ui.foundation.container

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import com.ekades.fcmpushnotification.lib.core.ui.extension.setViewMargin
import com.ekades.fcmpushnotification.lib.core.ui.extension.setViewPadding
import com.ekades.fcmpushnotification.lib.core.ui.foundation.spacing.Spacing

/**
 * Created by kevingozali on 05/30/20.
 */
interface Container {
    fun getView(): View

    /**
     * Set [Container] params
     * @param width  [ViewGroup.LayoutParams.MATCH_PARENT] or [ViewGroup.LayoutParams.WRAP_CONTENT] or [Int]
     * @param height [ViewGroup.LayoutParams.MATCH_PARENT] or [ViewGroup.LayoutParams.WRAP_CONTENT] or [Int]
     */
    fun setContainerParams(
        width: Int? = null,
        height: Int? = null
    ) {
        getView().layoutParams?.let {
            val params = getView().layoutParams
            params.width = width ?: params.width
            params.height = height ?: params.height
            getView().layoutParams = params
        } ?: run {
            getView().layoutParams = ViewGroup.MarginLayoutParams(
                width ?: ViewGroup.LayoutParams.MATCH_PARENT,
                height ?: ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    /**
     * Returns [Container] params
     * @return [ViewGroup.LayoutParams]
     */
    fun getContainerParams(): ViewGroup.LayoutParams {
        val layoutParams = getView().layoutParams
        return layoutParams ?: run {

            ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    /**
     * Set component visibility
     * @param visibility should be [View.VISIBLE], [View.INVISIBLE], or [View.GONE]
     */
    fun setContainerVisibility(visibility: Int) {
        getView().visibility = visibility
    }

    /**
     * Set OnClickListener to [Container]
     * @param onClickListener The listener
     */
    fun setOnClickListener(onClickListener: ((View) -> Unit)?) {
        getView().setOnClickListener(onClickListener)
    }

    /**
     * Set background drawable to [Container]
     * @param background [Drawable]
     */
    fun setContainerBackground(background: Drawable?) {
        getView().apply {
            /*
             * set padding is necessary
             * because, padding will be altered when you set new background
             */
            val leftPadding = paddingLeft
            val rightPadding = paddingRight
            val topPadding = paddingTop
            val bottomPadding = paddingBottom
            this.background = background
            setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
        }
    }

    /**
     * Set padding to [Container]
     * @param horizontal The left and right padding
     * @param vertical The top and bottom padding*/
    fun setContainerPadding(horizontal: Spacing, vertical: Spacing) {
        setContainerPadding(horizontal, vertical, horizontal, vertical)
    }

    /**
     * Set padding to [Container]
     * @param left The left padding
     * @param top The top padding
     * @param right The right padding
     * @param bottom The bottom padding*/

    fun setContainerPadding(
        left: Spacing? = null,
        top: Spacing? = null,
        right: Spacing? = null,
        bottom: Spacing? = null
    ) {
        getView().setViewPadding(left, top, right, bottom)
    }

    /**
     * Set margin to [Container]
     * @param horizontal The left and right margin
     * @param vertical The top and bottom margin*/

    fun setContainerMargin(horizontal: Spacing, vertical: Spacing) {
        setContainerMargin(horizontal, vertical, horizontal, vertical)
    }

    /**
     * Set margin to [Container]
     * @param left The left margin
     * @param top The top margin
     * @param right The right margin
     * @param bottom The bottom margin*/

    fun setContainerMargin(
        left: Spacing,
        top: Spacing,
        right: Spacing,
        bottom: Spacing
    ) {
        if (getView().layoutParams is ViewGroup.MarginLayoutParams) {
            getView().setViewMargin(left, top, right, bottom)
        }
    }

    companion object{}
}