package com.ekades.movieapps.features.murottal

import androidx.lifecycle.viewModelScope
import com.ekades.movieapps.networksV2.quran.domain.interactor.GetAllQuranSurahInteractor
import com.ekades.movieapps.features.quranlist.model.Surah
import com.ekades.movieapps.lib.application.viewmodel.BaseViewModel
import com.ekades.movieapps.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
import com.ekades.movieapps.lib.core.networkV2.utils.io
import com.ekades.movieapps.lib.core.networkV2.utils.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MurottalListViewModel(
    private val getAllQuranSurahInteractor: GetAllQuranSurahInteractor
) : BaseViewModel() {

    private val mQuranSurahList = arrayListOf<Surah>()
    val shownQuranSurahList = mutableLiveDataOf<List<Surah>>()
    val showLoading = mutableLiveDataOf<Boolean>()
    val selectedSurah = mutableLiveDataOf<Surah>()
    val mIsPlayingMurottal = mutableLiveDataOf<Boolean>()
    var surahIndex: Int? = null
    val isFirst: Boolean
        get() = surahIndex == 0
    val isLast: Boolean
        get() = surahIndex == (mQuranSurahList.size - 1)

    val isPlayingMurottal: Boolean
        get() = mIsPlayingMurottal.value == true

    var page = 1

    fun doPlayMurottal(isPlaying: Boolean = true) {
        mIsPlayingMurottal.value = isPlaying
    }

    fun getAllQuranSurah() {
        showLoading.value = true
        viewModelScope.launch {
            try {
                io {
                    getAllQuranSurahInteractor.execute(Interactor.None)
                        .collect {
                            ui {
                                mQuranSurahList.addAll(it)
                                setDefaultShownQuranSurah()
                            }
                        }
                }
            } catch (e: Exception) {
                showLoading.value = false
            }
        }
    }

    private fun setDefaultShownQuranSurah() {
        val maxPosition = page * 15

        if (maxPosition < mQuranSurahList.size) {
            shownQuranSurahList.value = mQuranSurahList.subList(0, maxPosition)
        } else if ((maxPosition - 15) <= mQuranSurahList.size) {
            shownQuranSurahList.value = mQuranSurahList.subList(0, mQuranSurahList.size)
        }
    }

    fun onLoadMore() {
        page++
        setDefaultShownQuranSurah()
    }

    fun setSelectedSurah(surah: Surah, index: Int) {
        this.surahIndex = index
        selectedSurah.value = surah
        updatePlayingSurah(index)
    }

    fun onClickPrevious() {
        surahIndex?.apply {
            val currentIndex = this - 1
            mQuranSurahList.getOrNull(currentIndex)?.apply {
                setSelectedSurah(this, currentIndex)
            }
        }
    }

    fun onClickNext() {
        surahIndex?.apply {
            val currentIndex = this + 1
            mQuranSurahList.getOrNull(currentIndex)?.apply {
                setSelectedSurah(this, currentIndex)
            }
        }
    }

    private fun updatePlayingSurah(index: Int) {
        shownQuranSurahList.value?.apply {
            find { it.isSelected }?.apply {
                isPlaying = false
                isSelected = false
            }

            getOrNull(index)?.apply {
                isPlaying = true
                isSelected = true
            }

            shownQuranSurahList.value = this
        }
    }

    fun setPlayingSurah() {
        selectedSurah.value?.apply {
            isPlaying = isPlaying.not()
            doPlayMurottal(isPlaying)
        }

        shownQuranSurahList.value?.apply {
            shownQuranSurahList.value = this
        }
    }
}