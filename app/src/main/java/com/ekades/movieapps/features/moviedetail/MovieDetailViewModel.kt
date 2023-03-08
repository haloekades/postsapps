package com.ekades.movieapps.features.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekades.movieapps.features.genre.state.GenreVS
import com.ekades.movieapps.features.moviedetail.state.MovieDetailVS
import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetAllGenresInteractor
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetMovieDetailInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDetailViewModel(
    private val getMovieDetailInteractor: GetMovieDetailInteractor
) : ViewModel() {

    val viewState: LiveData<MovieDetailVS> get() = mViewState
    private val mViewState = MutableLiveData<MovieDetailVS>()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            try {
                showLoader(true)
                getMovieDetailInteractor.execute(
                    GetMovieDetailInteractor.Params(
                        movieId = movieId
                    )
                ).collect {
                    mViewState.value = MovieDetailVS.ShowMovieDetail(it)
                    showLoader(false)
                }
            } catch (e: Exception) {
                mViewState.value = MovieDetailVS.Error(e.message.orEmpty())
                showLoader(false)
            }
        }
    }

    private fun showLoader(isShow: Boolean) {
        mViewState.value = MovieDetailVS.ShowLoader(isShow)
    }
}