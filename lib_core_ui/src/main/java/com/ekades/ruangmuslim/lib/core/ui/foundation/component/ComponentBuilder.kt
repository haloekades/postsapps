package com.ekades.ruangmuslim.lib.core.ui.foundation.component

import android.content.Context
import android.view.View
import android.view.ViewGroup

interface ComponentBuilder<V : View?> {
    fun build(ctx: Context, parent: ViewGroup): V
}