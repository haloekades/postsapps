package com.ekades.poststest.features.prayerschedule.prayerscheduledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ekades.poststest.features.networksV2.domain.interactor.GetPrayerScheduleTodayInteractor
import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.model.PrayerCitySchedule
import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.state.PrayerScheduleTodayVS
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.lib.core.networkV2.utils.io
import com.ekades.poststest.lib.core.networkV2.utils.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PrayerScheduleDetailViewModel(
    private val getPrayerScheduleTodayInteractor: GetPrayerScheduleTodayInteractor
) : BaseViewModel() {

    val viewState: LiveData<PrayerScheduleTodayVS> get() = mViewState
    private val mViewState = MutableLiveData<PrayerScheduleTodayVS>()

    fun getPrayerScheduleToday(cityId: String) {
        viewModelScope.launch {
            try {
                showLoader(true)
                io {
                    val params = GetPrayerScheduleTodayInteractor.Params(
                        cityId, "2022", "09", "24"
                    )
                    getPrayerScheduleTodayInteractor.execute(params = params)
                        .collect {
                            ui {
                                if (it.status && it.data != null) {
                                    onSuccess(it.data)
                                } else {
                                    onFailed(it.message.orEmpty())
                                }
                            }
                        }
                }
            } catch (e: Exception) {
                onFailed(e.message.orEmpty())
            }
        }
    }

    private fun onSuccess(prayerCitySchedule: PrayerCitySchedule) {
        mViewState.value = PrayerScheduleTodayVS.Success(prayerCitySchedule)
        showLoader(false)
    }

    private fun onFailed(errorMessage: String) {
        mViewState.value = PrayerScheduleTodayVS.Error(errorMessage)
        showLoader(false)
    }

    private fun showLoader(isShow: Boolean) {
        mViewState.value = PrayerScheduleTodayVS.ShowLoader(isShow)
    }
}