package com.ekades.movieapps.lib.core.network
import com.ekades.movieapps.lib.core.network.utils.constants.ApiMethod
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.interceptors.loggingRequestInterceptor

open class RemoteDataSource(method: ApiMethod = ApiMethod.GET): NetworkApi(method) {

    init {
        FuelManager.instance.addRequestInterceptor(loggingRequestInterceptor())
    }
}