package com.ekades.movieapps.lib.core.ui.extension

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekades.movieapps.lib.core.ui.foundation.component.Component
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.commons.utils.DiffCallback
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil

/**
 * Create a Recyclerview Adapter with [LinearLayoutManager]
 * @param context [Context]
 * @param orientation [RecyclerView.VERTICAL] or [RecyclerView.HORIZONTAL]
 */
@Suppress("UNCHECKED_CAST")
fun RecyclerView.linearLayoutAdapter(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL
): FastItemAdapter<Component<*>> {
    val adapter: FastItemAdapter<Component<*>>
    if (this.adapter == null) {
        adapter = FastItemAdapter()
        if (this.layoutManager == null) {
            val lm = LinearLayoutManager(context)
            lm.orientation = orientation
            this.layoutManager = lm
        }

        this.adapter = adapter
    } else {
        adapter = this.adapter as FastItemAdapter<Component<*>>
    }
    return adapter
}


/**
 * Create a Recyclerview Adapter with [GridLayoutManager]
 * @param context [Context]
 * @param span column count as [Int]
 */
@Suppress("UNCHECKED_CAST")
fun RecyclerView.gridLayoutAdapter(
    context: Context,
    span: Int
): FastItemAdapter<Component<*>> {
    val adapter: FastItemAdapter<Component<*>>
    if (this.adapter == null) {
        adapter = FastItemAdapter()
        if (this.layoutManager == null) {
            val lm = GridLayoutManager(context, span)
            this.layoutManager = lm
        }

        this.adapter = adapter
    } else {
        adapter = this.adapter as FastItemAdapter<Component<*>>
    }
    return adapter
}

/**
 * Create a Recyclerview Adapter with Staggered [GridLayoutManager]
 * @param span column count as [Int]
 * @param orientation [RecyclerView.VERTICAL] or [RecyclerView.HORIZONTAL]
 */
@Suppress("UNCHECKED_CAST")
fun RecyclerView.staggeredGridLayoutAdapter(
    span: Int,
    orientation: Int
): FastItemAdapter<Component<*>> {
    val adapter: FastItemAdapter<Component<*>>
    if (this.adapter == null) {
        adapter = FastItemAdapter()
        if (this.layoutManager == null) {
            val lm = StaggeredGridLayoutManager(span, orientation)
            this.layoutManager = lm
        }

        this.adapter = adapter
    } else {
        adapter = this.adapter as FastItemAdapter<Component<*>>
    }
    return adapter
}

/**
 * Create an Endless Recyclerview Scroll Listener
 * @param onReachEnd calls when RecyclerView scrolled to bottom
 */

fun <I : Component<*>> FastItemAdapter<I>.diffCalculateAdapter(
    newItems: MutableList<I>,
    detectMoves: Boolean = false
) {
    try {
        val diffCallback = object : DiffCallback<I> {
            override fun areItemsTheSame(oldItem: I, newItem: I): Boolean {
                return oldItem.getIdentifier() == newItem.getIdentifier()
            }
            
            override fun areContentsTheSame(oldItem: I?, newItem: I?): Boolean {
                return true
            }
            
            override fun getChangePayload(oldItem: I?, oldItemPosition: Int, newItem: I?, newItemPosition: Int): Any? {
                return null
            }
        }
        
        FastAdapterDiffUtil.set(this.itemAdapter, newItems, diffCallback, detectMoves)
    } catch (exception: Exception) {
        setNewList(newItems)
    }
}

fun RecyclerView.infiniteScrollListener(visibleThreshold: Int, loadMoreListener: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
            val lastVisibleItemPosition = when (recyclerView.layoutManager) {
                is GridLayoutManager -> (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                is LinearLayoutManager -> (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                else -> 0
            }

            if (totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                loadMoreListener()
            }
        }
    })
}
