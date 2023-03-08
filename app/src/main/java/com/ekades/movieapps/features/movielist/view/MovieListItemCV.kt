package com.ekades.movieapps.features.movielist.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.movieapps.R
import com.ekades.movieapps.features.movielist.model.MovieModel
import com.ekades.movieapps.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.movieapps.lib.ui.component.misc.DividerCV
import kotlinx.android.synthetic.main.cv_prayer_list_item.view.*

class MovieListItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<MovieListItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var movie: MovieModel? = null
        var onItemClickListener: ((movieId: Int) -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_prayer_list_item, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        dividerCV.bind {
            dividerStyle = DividerCV.DividerStyle.STRAIGHT
        }
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            movie?.apply {
                nameTextView.text = title
                setOnClickListener {
                    onItemClickListener?.invoke(id)
                }
            }
        }
    }
}