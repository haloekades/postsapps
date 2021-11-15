package com.ekades.poststest.apps

import com.ekades.poststest.ui.main.MainViewModel
import com.ekades.poststest.ui.photo.PhotoDetailViewModel
import com.ekades.poststest.ui.post.PostDetailViewModel
import com.ekades.poststest.ui.user.UserDetailViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

object Modules {

    fun getModules(): List<Module> {
        val module = module {
            viewModel { MainViewModel() }
            viewModel { PostDetailViewModel() }
            viewModel { UserDetailViewModel() }
            viewModel { PhotoDetailViewModel() }
        }
        return listOf(module)
    }
}