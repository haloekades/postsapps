package com.ekades.poststest.di

import com.ekades.poststest.features.main.MainViewModel
import com.ekades.poststest.features.networksV2.data.dataSource.PrayerScheduleDataStore
import com.ekades.poststest.features.networksV2.data.repository.PrayerScheduleRepositoryImpl
import com.ekades.poststest.features.networksV2.domain.interactor.GetAllCityInteractor
import com.ekades.poststest.features.networksV2.domain.interactor.GetPrayerScheduleTodayInteractor
import com.ekades.poststest.features.networksV2.domain.repository.PrayerScheduleRepository
import com.ekades.poststest.features.post.presentation.viewModels.MainV2ViewModel
import com.ekades.poststest.features.prayerdetail.PrayerDetailViewModel
import com.ekades.poststest.features.prayerlist.PrayerListViewModel
import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.PrayerScheduleDetailViewModel
import com.ekades.poststest.features.prayerschedule.searchcity.SearchCityViewModel
import com.ekades.poststest.features.users.presentation.viewModels.UserListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

private val postModule = module {

    //region ViewModel
    viewModel {
        MainV2ViewModel(get())
    }
    viewModel {
        UserListViewModel(get(), get())
    }
    //endregion

    //Teman Doa

    viewModel {
        MainViewModel()
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
//    single {
//        GetPostsInteractor(
//            get()
//        )
//    }
//    single {
//        GetPostByIdInteractor(
//            get()
//        )
//    }
//    single {
//        GetUsersInteractor(get())
//    }
//    single {
//        GetUserByLoginInteractor(get())
//    }
    single {
        GetAllCityInteractor(get())
    }

    single {
        GetPrayerScheduleTodayInteractor(get())
    }
    //endregion

    //region Repository
//    single<PostRepository> {
//        PostRepositoryImpl(get())
//    }
//    single<UserRepository> {
//        UserRepositoryImpl(get())
//    }
    single<PrayerScheduleRepository> {
        PrayerScheduleRepositoryImpl(get())
    }
    //endregion

    //region Datastore
//    single {
//        PostRestDataStore()
//    }
//    single {
//        UserRestDataStore()
//    }
    single {
        PrayerScheduleDataStore()
    }
    //endregion
}

val modules = listOf(postModule)