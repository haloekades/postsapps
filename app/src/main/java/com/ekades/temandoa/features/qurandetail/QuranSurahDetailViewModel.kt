package com.ekades.temandoa.features.qurandetail

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.ekades.temandoa.features.qurandetail.QuranSurahDetailActivity.Companion.EXTRA_SURAH
import com.ekades.temandoa.features.qurandetail.model.SurahDetail
import com.ekades.temandoa.features.quranlist.model.Surah
import com.ekades.temandoa.lib.application.viewmodel.BaseViewModel
import com.ekades.temandoa.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.temandoa.lib.core.networkV2.utils.io
import com.ekades.temandoa.lib.core.networkV2.utils.ui
import com.ekades.temandoa.networksV2.quran.domain.interactor.GetQuranSurahDetailInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QuranSurahDetailViewModel(
    private val getQuranSurahDetailInteractor: GetQuranSurahDetailInteractor
) : BaseViewModel() {

    private val mQuranSurahDetail = arrayListOf<SurahDetail>()
    val shownQuranSurahDetail = mutableLiveDataOf<List<SurahDetail>>()
    val showLoading = mutableLiveDataOf<Boolean>()

    var surah: Surah? = null
    var page = 1

    fun processIntent(intent: Intent?) {
        if (intent != null) {
            surah = intent.getParcelableExtra(EXTRA_SURAH)
        }
    }

    fun getQuranSurahDetail() {
        showLoading.value = true
        viewModelScope.launch {
            try {
                io {
                    getQuranSurahDetailInteractor.execute(
                        GetQuranSurahDetailInteractor.Params(
                            surah?.nomor ?: ""
                        )
                    )
                        .collect {
                            ui {
                                mQuranSurahDetail.addAll(it)
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
        val maxPosition = page * 25

        if (maxPosition < mQuranSurahDetail.size) {
            shownQuranSurahDetail.value = mQuranSurahDetail.subList(0, maxPosition)
        } else if ((maxPosition - 25) <= mQuranSurahDetail.size) {
            shownQuranSurahDetail.value = mQuranSurahDetail.subList(0, mQuranSurahDetail.size)
        }
    }

    fun onLoadMore() {
        page++
        setDefaultShownQuranSurah()
    }
}