package com.ekades.movieapps.features.movielist

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.ekades.movieapps.R
import com.ekades.movieapps.features.moviedetail.MovieDetailActivity
import com.ekades.movieapps.features.movielist.model.MovieModel
import com.ekades.movieapps.features.movielist.state.MovieListVS
import com.ekades.movieapps.features.movielist.view.MovieListItemCV
import com.ekades.movieapps.lib.application.ui.CoreActivity
import com.ekades.movieapps.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.movieapps.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.movieapps.lib.core.ui.extension.newComponent
import com.ekades.movieapps.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.movieapps.lib.core.ui.foundation.color.ColorPalette
import com.ekades.movieapps.lib.core.ui.foundation.component.Component
import com.ekades.movieapps.lib.core.ui.foundation.component.Rectangle
import com.ekades.movieapps.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.movieapps.lib.core.ui.foundation.container.LinearContainer
import com.ekades.movieapps.lib.core.ui.foundation.spacing.Spacing
import com.ekades.movieapps.lib.ui.asset.extension.dp
import com.ekades.movieapps.lib.ui.component.loading.RectangleSkeletonCV
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.coroutines.*


class MovieListActivity : CoreActivity<MovieListViewModel>(MovieListViewModel::class) {

    private val adapter by lazy {
        rvPrayerTime?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_movie_list
    }

    override fun render() = launch(Dispatchers.Main) {
        viewModel.setGenreId(intent)
        showToolbar()
        registerObeserver()
        renderViewTopRound()
        setupRecyclerView()
        viewModel.getMoviesByGenre()
    }

    private fun renderViewTopRound() {
        viewTopRound.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1, ColorPalette.WHITE)
        }
    }

    private fun registerObeserver() {
        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                is MovieListVS.ShowMovies -> renderList(viewState.movies)
                is MovieListVS.Error -> Toast.makeText(this, viewState.message, Toast.LENGTH_SHORT)
                    .show()
                is MovieListVS.ShowLoader -> {
                    if (viewState.showLoader) {
                        showLoadingView()
                    }
                }
            }
        })
    }

    private fun showToolbar() {
        toolbarCV.bind {
            onClickBackListener = {
                onBackPressed()
            }
            toolbarTitle = "Movie"
        }
    }

    private fun setupRecyclerView() {
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight &&
                    scrollY > oldScrollY
                ) {
                    viewModel.onLoadMore()
                }
            }
        }
    }

    private fun renderList(citySections: List<MovieModel>) {
        val components: MutableList<Component<*>> = citySections.mapIndexed { i, item ->
            ConstraintContainer.newComponent({
                MovieListItemCV(this)
            }) {
                movie = item
                onItemClickListener = { movieId ->
                    openMovieDetail(movieId)
                }
            }.setIdentifier("${item.id}")
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun openMovieDetail(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }

    private fun showLoadingView() {
        val loadingComponents: MutableList<Component<*>> = mutableListOf()
        loadingComponents.addAll(getLoadingComponents())
        adapter?.setNewList(loadingComponents)
    }

    private fun getLoadingComponents(): MutableList<Component<RectangleSkeletonCV>> {
        val component: MutableList<Component<RectangleSkeletonCV>> = arrayListOf()
        for (i in 0 until 10) {
            component.add(LinearContainer.newComponent({
                RectangleSkeletonCV(this)
            }) {
                height = 70.dp()
                componentMargin = Rectangle(
                    vertical = Spacing.x4,
                    horizontal = Spacing.x12
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }


    companion object {
        const val EXTRA_GENRE_ID = "extra_genre_id"

        @JvmStatic
        fun newIntent(context: Context?, genreId: Int): Intent {
            return Intent(context, MovieListActivity::class.java).apply {
                putExtra(EXTRA_GENRE_ID, genreId)
            }
        }
    }
}