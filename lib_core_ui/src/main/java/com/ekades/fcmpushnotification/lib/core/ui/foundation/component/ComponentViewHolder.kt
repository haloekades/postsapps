package com.ekades.fcmpushnotification.lib.core.ui.foundation.component

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ComponentViewHolder<V : View?>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val view: V = itemView as V
}