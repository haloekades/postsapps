package com.ekades.poststest.features.prayerschedule.searchcity

import androidx.lifecycle.viewModelScope
import com.ekades.poststest.features.networksV2.domain.interactor.GetAllCityInteractor
import com.ekades.poststest.features.prayerschedule.searchcity.model.CityItem
import com.ekades.poststest.lib.application.preferences.TemanDoaSession
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.poststest.lib.core.networkV2.interactor.Interactor
import com.ekades.poststest.lib.core.networkV2.utils.io
import com.ekades.poststest.lib.core.networkV2.utils.ui
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchCityViewModel(
    private val getAllCityInteractor: GetAllCityInteractor
) : BaseViewModel() {

    private val cityList = arrayListOf<CityItem>()
    val shownCityList = mutableLiveDataOf<List<CityItem>>()
    val showLoading = mutableLiveDataOf<Boolean>()

    var page = 1

    fun getAllCity() {
        showLoading.value = true
        getMasterCityListFromSession()?.takeIf { it.isNotEmpty() }?.apply {
            cityList.addAll(this)
            setDefaultShownCity()
            showLoading.value = false
            return
        }

        viewModelScope.launch {
            try {
                io {
                    getAllCityInteractor.execute(Interactor.None)
                        .collect {
                            ui {
                                cityList.addAll(it)
                                saveMasterCityList(it)
                                setDefaultShownCity()
                            }
                        }
                }
            } catch (e: Exception) {
                showLoading.value = false
            }
        }
    }

    private fun saveMasterCityList(cityList: List<CityItem>) {
        TemanDoaSession.masterCityList = Gson().toJson(cityList)
    }

    private fun getMasterCityListFromSession(): List<CityItem>? {
        return TemanDoaSession.masterCityList.takeIf { it.isNotEmpty() }?.let {
            Gson().fromJson<List<CityItem>>(it, object : TypeToken<List<CityItem>>() {}.type)
        }
    }


    fun onSearchCity(cityName: String) {
        page = 1
        if (cityName.isNotBlank()) {
            val shownCities = cityList.filter { it.lokasi.contains(cityName, true) }
            shownCityList.value = shownCities
        } else {
            setDefaultShownCity()
        }
    }

    private fun setDefaultShownCity() {
        val maxPosition = page * 100

        if (maxPosition < cityList.size) {
            shownCityList.value = cityList.subList(0, maxPosition)
        } else if ((maxPosition - 100) <= cityList.size) {
            shownCityList.value = cityList.subList(0, cityList.size)
        }
    }

    fun onLoadMore() {
        page++
        setDefaultShownCity()
    }
}