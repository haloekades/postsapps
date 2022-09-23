package com.ekades.fcmpushnotification.apps

import com.ekades.fcmpushnotification.ui.main.MainViewModel
import com.ekades.fcmpushnotification.ui.photo.PhotoDetailViewModel
import com.ekades.fcmpushnotification.ui.post.PostDetailViewModel
import com.ekades.fcmpushnotification.ui.user.UserDetailViewModel
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