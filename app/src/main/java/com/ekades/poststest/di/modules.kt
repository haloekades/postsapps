package com.ekades.poststest.di

import com.carlosgub.coroutines.features.books.domain.interactor.GetPostByIdInteractor
import com.carlosgub.coroutines.features.books.domain.interactor.GetUserByLoginInteractor
import com.ekades.poststest.features.post.domain.interactor.GetPostsInteractor
import com.ekades.poststest.features.post.data.dataSource.PostRestDataStore
import com.ekades.poststest.features.post.data.repository.PostRepositoryImpl
import com.ekades.poststest.features.post.domain.repository.PostRepository
import com.ekades.poststest.features.post.presentation.viewModels.MainV2ViewModel
import com.ekades.poststest.features.users.data.dataSource.UserRestDataStore
import com.ekades.poststest.features.users.data.repository.UserRepositoryImpl
import com.ekades.poststest.features.users.domain.interactor.GetUsersInteractor
import com.ekades.poststest.features.users.domain.repository.UserRepository
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
    single {
        GetUsersInteractor(get())
    }
    single {
        GetUserByLoginInteractor(get())
    }
    //endregion

    //region Repository
//    single<PostRepository> {
//        PostRepositoryImpl(get())
//    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    //endregion

    //region Datastore
//    single {
//        PostRestDataStore()
//    }
    single {
        UserRestDataStore()
    }
    //endregion
}

val modules = listOf(postModule)