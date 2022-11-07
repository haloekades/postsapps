package com.ekades.temandoa.features.quranlist

import androidx.lifecycle.viewModelScope
import com.ekades.temandoa.networksV2.quran.domain.interactor.GetAllQuranSurahInteractor
import com.ekades.temandoa.features.quranlist.model.Surah
import com.ekades.temandoa.lib.application.viewmodel.BaseViewModel
import com.ekades.temandoa.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.temandoa.lib.core.networkV2.interactor.Interactor
import com.ekades.temandoa.lib.core.networkV2.utils.io
import com.ekades.temandoa.lib.core.networkV2.utils.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QuranSurahListViewModel(
    private val getAllQuranSurahInteractor: GetAllQuranSurahInteractor
) : BaseViewModel() {

    private val mQuranSurahList = arrayListOf<Surah>()
    val shownQuranSurahList = mutableLiveDataOf<List<Surah>>()
    val showLoading = mutableLiveDataOf<Boolean>()

    var page = 1

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
        viewModelScope.launch {
            io {
                val maxPosition = page * 15

                if (maxPosition < mQuranSurahList.size) {
                    ui {
                        shownQuranSurahList.value = mQuranSurahList.subList(0, maxPosition)
                    }
                } else if ((maxPosition - 15) <= mQuranSurahList.size) {
                    ui {
                        shownQuranSurahList.value = mQuranSurahList.subList(0, mQuranSurahList.size)
                    }
                }
            }
        }
    }

    fun onLoadMore() {
        page++
        setDefaultShownQuranSurah()
    }
}