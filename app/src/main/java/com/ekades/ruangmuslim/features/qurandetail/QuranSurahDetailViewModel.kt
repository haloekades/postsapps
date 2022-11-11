package com.ekades.ruangmuslim.features.qurandetail

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.ekades.ruangmuslim.features.qurandetail.QuranSurahDetailActivity.Companion.EXTRA_SURAH
import com.ekades.ruangmuslim.features.qurandetail.model.SurahDetail
import com.ekades.ruangmuslim.features.quranlist.model.Surah
import com.ekades.ruangmuslim.lib.application.viewmodel.BaseViewModel
import com.ekades.ruangmuslim.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.ruangmuslim.lib.core.networkV2.utils.io
import com.ekades.ruangmuslim.lib.core.networkV2.utils.ui
import com.ekades.ruangmuslim.networksV2.quran.domain.interactor.GetQuranSurahDetailInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QuranSurahDetailViewModel(
    private val getQuranSurahDetailInteractor: GetQuranSurahDetailInteractor
) : BaseViewModel() {

    private val mQuranSurahDetail = arrayListOf<SurahDetail>()
    val shownQuranSurahDetail = mutableLiveDataOf<List<SurahDetail>>()
    val showLoading = mutableLiveDataOf<Boolean>()
    val mIsPlayingMurottal = mutableLiveDataOf<Boolean>()

    val isPlayingMurottal: Boolean
        get() = mIsPlayingMurottal.value == true

    var surah: Surah? = null
    var page = 1

    fun onClickAudioAction() {
        mIsPlayingMurottal.value = isPlayingMurottal.not()
    }

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

        viewModelScope.launch {
            io {
                val maxPosition = page * 10

                if (maxPosition < mQuranSurahDetail.size) {
                    ui {
                        shownQuranSurahDetail.value = mQuranSurahDetail.subList(0, maxPosition)
                    }
                } else if ((maxPosition - 10) <= mQuranSurahDetail.size) {
                    ui {
                        shownQuranSurahDetail.value = mQuranSurahDetail.subList(0, mQuranSurahDetail.size)
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