package com.ekades.movieapps.lib.core.ui.foundation.container

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

abstract class FrameContainer<S> constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle), Container {
    /**
     * Static companion object to be injected
     * @see
     */
    companion object {}

    /**
     * State of [FrameContainer]
     */
    protected var state: S = this.initState()

    /**
     * State of [FrameContainer]
     * @param [S] as [State]
     */
    protected abstract fun initState(): S

    /**
     * Called when [Container] is attached to [RecyclerView]
     * @param [S] as [State]
     */
    protected abstract fun render(state: S)

    /**
     * @return This as [View]
     */
    override fun getView(): View = this

    /**
     * Bind [State] to [View], gets called when attached from [RecyclerView]
     */
    open fun bind(newState: S) {
        state = newState
        render(state)
    }

    /**
     * Patch [State] to [View], gets called when attached from [RecyclerView]
     */
    open fun bind(patchState: S.() -> Unit) {
        state.patchState()
        bind(state)
    }

    /**
     * Unbind [State] from [View], gets called when detached from [RecyclerView]
     */
    open fun unbind() {
        state = initState()
    }
}