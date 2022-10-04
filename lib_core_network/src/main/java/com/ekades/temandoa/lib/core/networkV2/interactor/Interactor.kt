package com.ekades.temandoa.lib.core.networkV2.interactor

interface Interactor<in Params, out Type> {

    fun execute(params: Params): Type

    object None
}