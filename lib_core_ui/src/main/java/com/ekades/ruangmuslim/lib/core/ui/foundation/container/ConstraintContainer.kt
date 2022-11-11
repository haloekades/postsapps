package com.ekades.ruangmuslim.lib.core.ui.foundation.container

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

abstract class ConstraintContainer<S> constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle), Container {
    /**
     * Static companion object to be injected
     * @see [ConstraintContainer.Companion]
     */
    companion object {}

    /**
     * State of [ConstraintContainer]
     */
    private var state: S = this.initState()

    /**
     * State of [ConstraintContainer]
     * @param [S] as [State]
     */
    protected abstract fun initState(): S

    /**
     * Called when [Container] is attached to [RecyclerView]
     * @param [State]
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