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
        rvPrayerTime?.gridLayoutAdapter(this, 2)
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

    private fun renderGenres(mainSections: List<GenreModel>) {
        val components: MutableList<Component<*>> = mainSections.map { item ->
            ConstraintContainer.newComponent({
                GenreItemCV(this)
            }) {
                section = item
                onItemClickListener = { genreId ->
                    openMovieList(genreId)
                }
            }.setIdentifier(item.id.toString())
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun openMovieList(genreId: Int) {
        startActivity(MovieListActivity.newIntent(this, genreId))
    }

    companion object {

        @JvmStatic
        fun newIntent(context: Context?): Intent {
            return Intent(context, GenreActivity::class.java)
        }
    }
}