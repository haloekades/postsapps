package com.ekades.fcmpushnotification.lib.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

fun <T> ViewModel.mutableLiveDataOf() = MutableLiveData<T>()