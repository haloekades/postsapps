package com.ekades.ruangmuslim.di

import com.ekades.ruangmuslim.features.main.MainViewModel
import com.ekades.ruangmuslim.features.murottal.MurottalListViewModel
import com.ekades.ruangmuslim.networksV2.prayerschedule.data.dataSource.PrayerScheduleDataStore
import com.ekades.ruangmuslim.networksV2.prayerschedule.data.repository.PrayerScheduleRepositoryImpl
import com.ekades.ruangmuslim.networksV2.prayerschedule.domain.interactor.GetAllCityInteractor
import com.ekades.ruangmuslim.networksV2.prayerschedule.domain.interactor.GetPrayerScheduleMonthlyInteractor
import com.ekades.ruangmuslim.networksV2.prayerschedule.domain.interactor.GetPrayerScheduleTodayInteractor
import com.ekades.ruangmuslim.networksV2.prayerschedule.domain.repository.PrayerScheduleRepository
import com.ekades.ruangmuslim.networksV2.quran.data.repository.QuranRepositoryImpl
import com.ekades.ruangmuslim.networksV2.quran.data.dataSource.QuranDataStore
import com.ekades.ruangmuslim.networksV2.quran.domain.interactor.GetAllQuranSurahInteractor
import com.ekades.ruangmuslim.networksV2.quran.domain.repository.QuranRepository
import com.ekades.ruangmuslim.features.prayerdetail.PrayerDetailViewModel
import com.ekades.ruangmuslim.features.prayerlist.PrayerListViewModel
import com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.PrayerScheduleDetailViewModel
import com.ekades.ruangmuslim.features.prayerschedule.searchcity.SearchCityViewModel
import com.ekades.ruangmuslim.features.qurandetail.QuranSurahDetailViewModel
import com.ekades.ruangmuslim.features.quranlist.QuranSurahListViewModel
import com.ekades.ruangmuslim.networksV2.quran.domain.interactor.GetQuranSurahDetailInteractor
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

    viewModel {
        QuranSurahListViewModel(get())
    }

    viewModel {
        QuranSurahDetailViewModel(get())
    }

    viewModel {
        MurottalListViewModel(get())
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

    single {
        GetAllQuranSurahInteractor(get())
    }

    single {
        GetQuranSurahDetailInteractor(get())
    }
    //endregion

    //region Repository
    single<PrayerScheduleRepository> {
        PrayerScheduleRepositoryImpl(get())
    }

    single<QuranRepository> {
        QuranRepositoryImpl(get())
    }
    //endregion

    single {
        PrayerScheduleDataStore()
    }

    single {
        QuranDataStore()
    }
    //endregion
}

val modules = listOf(postModule)