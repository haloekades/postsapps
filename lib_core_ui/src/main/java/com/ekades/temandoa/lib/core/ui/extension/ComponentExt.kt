package com.ekades.temandoa.lib.core.ui.extension

import android.content.Context
import com.ekades.temandoa.lib.core.ui.foundation.component.Component
import com.ekades.temandoa.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.temandoa.lib.core.ui.foundation.container.FrameContainer
import com.ekades.temandoa.lib.core.ui.foundation.container.LinearContainer
import com.ekades.temandoa.lib.core.ui.foundation.container.RelativeContainer

/**
 * Create new instance of [LinearContainer]
 */
inline fun <S, reified C: LinearContainer<S>> LinearContainer.Companion.newComponent(
    crossinline component: (Context) -> C,
    itemType: Int = C::class.java.hashCode(),
    noinline state: S.() -> Unit = { }
): Component<C> {
    return Component(itemType) { context ->
        component(context)
    }.onAttached {
        it.bind(state)
    }.onDetached {
        it.unbind()
    }
}

/**
 * Create new instance of [ConstraintContainer]
 */
inline fun <S, reified C: ConstraintContainer<S>> ConstraintContainer.Companion.newComponent(
    crossinline component: (Context) -> C,
    itemType: Int = C::class.java.hashCode(),
    noinline state: S.() -> Unit = { }
): Component<C> {
    return Component(itemType) { context ->
        component(context)
    }.onAttached {
        it.bind(state)
    }.onDetached {
        it.unbind()
    }
}

/**
 * Create new instance of [FrameContainer]
 */
inline fun <S, reified C: FrameContainer<S>> FrameContainer.Companion.newComponent(
    crossinline component: (Context) -> C,
    itemType: Int = C::class.java.hashCode(),
    noinline state: S.() -> Unit = { }
): Component<C> {
    return Component(itemType) { context ->
        component(context)
    }.onAttached {
        it.bind(state)
    }.onDetached {
        it.unbind()
    }
}

/**
 * Create new instance of [RelativeContainer]
 */
inline fun <S, reified C: RelativeContainer<S>> RelativeContainer.Companion.newComponent(
    crossinline component: (Context) -> C,
    itemType: Int = C::class.java.hashCode(),
    noinline state: S.() -> Unit = { }
): Component<C> {
    return Component(itemType) { context ->
        component(context)
    }.onAttached {
        it.bind(state)
    }.onDetached {
        it.unbind()
    }
}