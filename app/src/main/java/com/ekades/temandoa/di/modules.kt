package com.ekades.temandoa.di

import com.ekades.temandoa.features.main.MainViewModel
import com.ekades.temandoa.features.networksV2.data.dataSource.PrayerScheduleDataStore
import com.ekades.temandoa.features.networksV2.data.repository.PrayerScheduleRepositoryImpl
import com.ekades.temandoa.features.networksV2.domain.interactor.GetAllCityInteractor
import com.ekades.temandoa.features.networksV2.domain.interactor.GetPrayerScheduleMonthlyInteractor
import com.ekades.temandoa.features.networksV2.domain.interactor.GetPrayerScheduleTodayInteractor
import com.ekades.temandoa.features.networksV2.domain.repository.PrayerScheduleRepository
import com.ekades.temandoa.features.prayerdetail.PrayerDetailViewModel
import com.ekades.temandoa.features.prayerlist.PrayerListViewModel
import com.ekades.temandoa.features.prayerschedule.prayerscheduledetail.PrayerScheduleDetailViewModel
import com.ekades.temandoa.features.prayerschedule.searchcity.SearchCityViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

private val postModule = module {

    //Teman Doa
    viewModel {
        MainViewModel(get())
    }

    viewModel {
        PrayerDetailViewModel()
    }

    viewModel {
        PrayerListViewModel()
    }

    viewModel {
        SearchCityViewModel(get())
    }

    viewModel {
        PrayerScheduleDetailViewModel(get())
    }

    //end Teman Doa


    //region Interactor
    single {
        GetAllCityInteractor(get())
    }

    single {
        GetPrayerScheduleTodayInteractor(get())
    }

    single {
        GetPrayerScheduleMonthlyInteractor(get())
    }
    //endregion

    //region Repository
    single<PrayerScheduleRepository> {
        PrayerScheduleRepositoryImpl(get())
    }
    //endregion

    single {
        PrayerScheduleDataStore()
    }
    //endregion
}

val modules = listOf(postModule)