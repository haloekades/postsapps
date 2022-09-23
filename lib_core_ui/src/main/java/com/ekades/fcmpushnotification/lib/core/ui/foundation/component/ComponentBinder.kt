package com.ekades.fcmpushnotification.lib.core.ui.foundation.component

import android.view.View

interface ComponentBinder<V : View?> {
    fun bind(view: V, item: Component<V>?)
}