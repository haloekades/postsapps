package com.ekades.movieapps.features.moviedetail

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ekades.movieapps.R
import com.ekades.movieapps.features.moviedetail.state.MovieDetailVS
import com.ekades.movieapps.lib.application.ui.CoreActivity
import com.ekades.movieapps.lib.core.ui.extension.loadImageUrlWithPlaceHolder
import com.ekades.movieapps.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.movieapps.lib.core.ui.foundation.color.ColorPalette
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieDetailResponse
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class MovieDetailActivity : CoreActivity<MovieDetailViewModel>(MovieDetailViewModel::class) {

    init {
        activityLayoutRes = R.layout.activity_movie_detail
    }

    override fun render() = launch(Dispatchers.Main) {
        registerObserver()
        renderToolbar()
        viewModel.getMovieDetail(intent.getIntExtra(EXTRA_MOVIE_ID, 0))
    }

    private fun registerObserver() {
        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                is MovieDetailVS.ShowMovieDetail -> renderMovieDetail(viewState.movieDetail)
                is MovieDetailVS.Error -> Toast.makeText(
                    this,
                    viewState.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
                is MovieDetailVS.ShowLoader -> {}
            }
        })
    }

    private fun renderToolbar() {
        toolbarCV.bind {
            toolbarTitle = getString(R.string.app_name)
        }
    }

    private fun renderMovieDetail(movieDetailResponse: MovieDetailResponse) {
        tvTitle.text = movieDetailResponse.title
        ivBackDrop.loadImageUrlWithPlaceHolder(BASE_IMAGE_URL + movieDetailResponse.backdropPath)
        ivPoster.loadImageUrlWithPlaceHolder(BASE_IMAGE_URL + movieDetailResponse.posterPath)
        tvDescription.text = movieDetailResponse.overview
    }

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"


        @JvmStatic
        fun newIntent(context: Context?, movieId: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
        }
    }
}