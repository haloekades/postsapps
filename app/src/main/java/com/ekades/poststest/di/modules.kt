package com.ekades.poststest.di

import com.carlosgub.coroutines.features.books.domain.interactor.GetPostByIdInteractor
import com.ekades.poststest.features.post.domain.interactor.GetPostsInteractor
import com.ekades.poststest.features.post.data.dataSource.PostRestDataStore
import com.ekades.poststest.features.post.data.repository.PostRepositoryImpl
import com.ekades.poststest.features.post.domain.repository.PostRepository
import com.ekades.poststest.features.post.presentation.viewModels.MainV2ViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

private val postModule = module {

    //region ViewModel
    viewModel {
        MainV2ViewModel(get())
    }
    //endregion

    //region Interactor
    single {
        GetPostsInteractor(
            get()
        )
    }
    single {
        GetPostByIdInteractor(
            get()
        )
    }
    //endregion

    //region Repository
    single<PostRepository> {
        PostRepositoryImpl(get())
    }
    //endregion

    //region Datastore
    single {
        PostRestDataStore()
    }
    //endregion
}

val modules = listOf(postModule)