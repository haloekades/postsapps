package com.ekades.movieapps.features.movielist.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.movieapps.R
import com.ekades.movieapps.features.movielist.model.MovieModel
import com.ekades.movieapps.lib.core.ui.extension.loadImageUrlWithPlaceHolder
import com.ekades.movieapps.lib.core.ui.foundation.background.shadowLarge
import com.ekades.movieapps.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.movieapps.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.movieapps.lib.core.ui.foundation.spacing.Spacing
import kotlinx.android.synthetic.main.cv_movie_list_item.view.*

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
        View.inflate(context, R.layout.cv_movie_list_item, this)
        setContainerParams(LayoutParams.MATCH_PARENT, Spacing.xImgHeigt.value)
        setContainerMargin(
            left = Spacing.x8,
            right = Spacing.x8,
            top = Spacing.x12,
            bottom = Spacing.x4
        )
        shadowLarge(CornerRadius.XLARGE)
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            movie?.apply {
                tvTitle.text = title
                ivMovie.loadImageUrlWithPlaceHolder("$BASE_IMAGE_URL$posterPath")
                setOnClickListener {
                    onItemClickListener?.invoke(id)
                }
            }
        }
    }

    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }
}