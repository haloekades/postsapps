package com.ekades.movieapps.di

import com.ekades.movieapps.features.genre.GenreViewModel
import com.ekades.movieapps.features.main.MainViewModel
import com.ekades.movieapps.features.mainmovie.MainMovieViewModel
import com.ekades.movieapps.features.moviedetail.MovieDetailViewModel
import com.ekades.movieapps.features.movielist.MovieListViewModel
import com.ekades.movieapps.features.murottal.MurottalListViewModel
import com.ekades.movieapps.networksV2.prayerschedule.data.dataSource.PrayerScheduleDataStore
import com.ekades.movieapps.networksV2.prayerschedule.data.repository.PrayerScheduleRepositoryImpl
import com.ekades.movieapps.networksV2.prayerschedule.domain.interactor.GetAllCityInteractor
import com.ekades.movieapps.networksV2.prayerschedule.domain.interactor.GetPrayerScheduleMonthlyInteractor
import com.ekades.movieapps.networksV2.prayerschedule.domain.interactor.GetPrayerScheduleTodayInteractor
import com.ekades.movieapps.networksV2.prayerschedule.domain.repository.PrayerScheduleRepository
import com.ekades.movieapps.networksV2.quran.data.repository.QuranRepositoryImpl
import com.ekades.movieapps.networksV2.quran.data.dataSource.QuranDataStore
import com.ekades.movieapps.networksV2.quran.domain.interactor.GetAllQuranSurahInteractor
import com.ekades.movieapps.networksV2.quran.domain.repository.QuranRepository
import com.ekades.movieapps.features.prayerdetail.PrayerDetailViewModel
import com.ekades.movieapps.features.prayerlist.PrayerListViewModel
import com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.PrayerScheduleDetailViewModel
import com.ekades.movieapps.features.prayerschedule.searchcity.SearchCityViewModel
import com.ekades.movieapps.features.qurandetail.QuranSurahDetailViewModel
import com.ekades.movieapps.features.quranlist.QuranSurahListViewModel
import com.ekades.movieapps.networksV2.movie.data.dataSource.MovieDataStore
import com.ekades.movieapps.networksV2.movie.data.repository.MovieRepositoryImpl
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetAllGenresInteractor
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetMovieDetailInteractor
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetMoviesByGenreInteractor
import com.ekades.movieapps.networksV2.movie.domain.repository.MovieRepository
import com.ekades.movieapps.networksV2.quran.domain.interactor.GetQuranSurahDetailInteractor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

private val postModule = module {

    // Start Movie //
    viewModel {
        GenreViewModel(get())
    }

    viewModel {
        MovieListViewModel(get())
    }

    viewModel {
        MovieDetailViewModel(get())
    }

    single {
        GetAllGenresInteractor(get())
    }

    single {
        GetMoviesByGenreInteractor(get())
    }

    single {
        GetMovieDetailInteractor(get())
    }

    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }

    single {
        MovieDataStore()
    }

    // End Movie


    viewModel {
        MainMovieViewModel(get())
    }

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