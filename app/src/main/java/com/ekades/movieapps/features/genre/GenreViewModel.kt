package com.ekades.movieapps.features.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekades.movieapps.features.genre.state.GenreVS
import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetAllGenresInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class GenreViewModel(
    private val getAllGenresInteractor: GetAllGenresInteractor
) : ViewModel() {

    val viewState: LiveData<GenreVS> get() = mViewState
    private val mViewState = MutableLiveData<GenreVS>()

    fun getAllGenres() {
        viewModelScope.launch {
            try {
                showLoader(true)
                getAllGenresInteractor.execute(Interactor.None).collect {
                    mViewState.value = GenreVS.ShowGenres(it)
                    showLoader(false)
                }
            } catch (e: Exception) {
                mViewState.value = GenreVS.Error(e.message.orEmpty())
                showLoader(false)
            }
        }
    }

    private fun showLoader(isShow: Boolean) {
        mViewState.value = GenreVS.ShowLoader(isShow)
    }
}