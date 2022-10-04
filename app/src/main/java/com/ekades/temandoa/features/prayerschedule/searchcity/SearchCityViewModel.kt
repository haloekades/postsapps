package com.ekades.temandoa.features.prayerschedule.searchcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekades.temandoa.features.networksV2.domain.interactor.GetAllCityInteractor
import com.ekades.temandoa.features.prayerschedule.searchcity.model.CityItem
import com.ekades.temandoa.lib.application.preferences.TemanDoaSession
import com.ekades.temandoa.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.temandoa.lib.core.networkV2.interactor.Interactor
import com.ekades.temandoa.lib.core.networkV2.utils.io
import com.ekades.temandoa.lib.core.networkV2.utils.ui
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchCityViewModel(
    private val getAllCityInteractor: GetAllCityInteractor
) : ViewModel() {

    private val cityList = arrayListOf<CityItem>()
    val shownCityList = mutableLiveDataOf<List<CityItem>>()
    val showLoading = mutableLiveDataOf<Boolean>()

    var page = 1
    private var searchCityName = ""

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
        searchCityName = cityName
        setDefaultShownCity()
    }

    private fun setDefaultShownCity() {
        val maxPosition = page * 25

        val filterCities = getCityListWithFilter()

        if (maxPosition < filterCities.size) {
            shownCityList.value = filterCities.subList(0, maxPosition)
        } else if ((maxPosition - 25) <= filterCities.size) {
            shownCityList.value = filterCities.subList(0, filterCities.size)
        }
    }

    private fun getCityListWithFilter(): ArrayList<CityItem> {
        return if (searchCityName.isNotBlank()) {
            ArrayList(cityList.filter { it.lokasi.contains(searchCityName, true) })
        } else {
            cityList
        }
    }

    fun onLoadMore() {
        page++
        setDefaultShownCity()
    }
}