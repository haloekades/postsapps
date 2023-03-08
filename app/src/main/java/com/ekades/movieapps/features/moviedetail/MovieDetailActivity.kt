package com.ekades.movieapps.features.moviedetail

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ekades.movieapps.R
import com.ekades.movieapps.features.moviedetail.state.MovieDetailVS
import com.ekades.movieapps.lib.application.ui.CoreActivity
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
        renderBgContentView()
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
        showToolbar()
        setupAppBarListener()
    }

    private fun setupAppBarListener() {
        titleCollapsingToolbarTextView.text = getString(R.string.app_name)
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                titleCollapsingToolbarTextView.alpha = 0F
                showToolbar()
            } else {
                titleCollapsingToolbarTextView.alpha = 1F
                showToolbar(false)
            }
        })
    }

    private fun showToolbar(isVisible: Boolean = true) {
        toolbarCV.bind {
            toolbarTitle = if (isVisible) {
                getString(R.string.app_name)
            } else {
                null
            }
        }
    }

    private fun renderBgContentView() {
        mainContentView.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.SNOW)
            setStroke(1, ColorPalette.WHITE)
        }
    }

    private fun renderMovieDetail(movieDetailResponse: MovieDetailResponse) {
        tvTitle.text = movieDetailResponse.title
    }

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        @JvmStatic
        fun newIntent(context: Context?, movieId: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
        }
    }
}