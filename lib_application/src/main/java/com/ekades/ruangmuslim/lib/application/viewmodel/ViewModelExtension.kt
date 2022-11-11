package com.ekades.ruangmuslim.lib.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

fun <T> ViewModel.mutableLiveDataOf() = MutableLiveData<T>()