package com.ekades.movieapps.features.genre

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ekades.movieapps.R
import com.ekades.movieapps.features.genre.model.GenreModel
import com.ekades.movieapps.features.genre.state.GenreVS
import com.ekades.movieapps.features.genre.view.GenreItemCV
import com.ekades.movieapps.features.movielist.MovieListActivity
import com.ekades.movieapps.lib.application.ui.CoreActivity
import com.ekades.movieapps.lib.core.ui.extension.*
import com.ekades.movieapps.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.movieapps.lib.core.ui.foundation.color.ColorPalette
import com.ekades.movieapps.lib.core.ui.foundation.component.Component
import com.ekades.movieapps.lib.core.ui.foundation.container.ConstraintContainer
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_genre.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class GenreActivity : CoreActivity<GenreViewModel>(GenreViewModel::class) {

    private val adapter by lazy {
        rvPrayerTime?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_genre
    }

    override fun render() = launch(Dispatchers.Main) {
        registerObserver()
        renderToolbar()
        renderBgContentView()
        viewModel.getAllGenres()
    }

    private fun registerObserver() {
        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                is GenreVS.ShowGenres -> renderGenres(viewState.genres)
                is GenreVS.Error -> Toast.makeText(this, viewState.message, Toast.LENGTH_SHORT)
                    .show()
                is GenreVS.ShowLoader -> {}
            }
        })
    }

    private fun renderToolbar() {
        toolbarCV.bind {
            toolbarTitle = getString(R.string.genre)
        }
    }

    private fun renderBgContentView() {
        mainContentView.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.SNOW)
            setStroke(1, ColorPalette.WHITE)
        }
    }

    private fun renderGenres(mainSections: List<GenreModel>) {
        val components: MutableList<Component<*>> = mainSections.map { item ->
            ConstraintContainer.newComponent({
                GenreItemCV(this)
            }) {
                section = item
                onItemClickListener = { genreId, genreName ->
                    openMovieList(genreId, genreName)
                }
            }.setIdentifier(item.id.toString())
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun openMovieList(genreId: Int, genreName: String) {
        startActivity(MovieListActivity.newIntent(this, genreId, genreName))
    }

    companion object {

        @JvmStatic
        fun newIntent(context: Context?): Intent {
            return Intent(context, GenreActivity::class.java)
        }
    }
}