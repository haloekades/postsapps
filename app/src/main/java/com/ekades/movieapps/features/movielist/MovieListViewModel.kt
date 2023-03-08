package com.ekades.movieapps.features.movielist

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekades.movieapps.features.movielist.MovieListActivity.Companion.EXTRA_GENRE_ID
import com.ekades.movieapps.features.movielist.model.MovieModel
import com.ekades.movieapps.features.movielist.state.MovieListVS
import com.ekades.movieapps.lib.core.networkV2.utils.io
import com.ekades.movieapps.lib.core.networkV2.utils.ui
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetMoviesByGenreInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val getMoviesByGenreInteractor: GetMoviesByGenreInteractor
) : ViewModel() {

    var page = 1
    private var genreId = 0
    private var isMax = false

    private val mMovies: ArrayList<MovieModel> = arrayListOf()

    val viewState: LiveData<MovieListVS> get() = mViewState
    private val mViewState = MutableLiveData<MovieListVS>()

    fun setGenreId(intent: Intent) {
        genreId = intent.getIntExtra(EXTRA_GENRE_ID, 0)
    }

    fun getMoviesByGenre() {
        viewModelScope.launch {
            try {
                ui {
                    if (page == 1) showLoader(true)
                }
                io {
                    getMoviesByGenreInteractor.execute(
                        GetMoviesByGenreInteractor.Params(
                            page, genreId
                        )
                    ).collect {
                        ui {
                            if (it.results.isNotEmpty()) {
                                updateMovies(it.results)
                                showLoader(false)
                            } else {
                                isMax = true
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                ui {
                    isMax = true
                    mViewState.value = MovieListVS.Error(e.message.orEmpty())
                    showLoader(false)
                }
            }
        }
    }

    fun onLoadMore() {
        if (isMax) return
        page++
        getMoviesByGenre()
    }

    private fun updateMovies(
        movies: List<MovieModel>
    ) {
        mMovies.addAll(movies)
        mViewState.value = MovieListVS.ShowMovies(mMovies)
    }

    private fun showLoader(isShow: Boolean) {
        mViewState.value = MovieListVS.ShowLoader(isShow)
    }
}