package com.ekades.fcmpushnotification.lib.application.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    val disposables = CompositeDisposable()

    var isLoading = mutableLiveDataOf<Boolean>()

    fun showLoading(isShow: Boolean = true) {
        isLoading.value = isShow
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}