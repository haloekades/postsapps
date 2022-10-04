package com.ekades.temandoa.lib.core.test.viewmodels.base

import androidx.lifecycle.MutableLiveData
import com.ekades.temandoa.lib.application.ApplicationProvider
import com.ekades.temandoa.lib.core.network.NetworkApi
import com.ekades.temandoa.lib.core.network.responses.ApiResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.disposables.Disposable

abstract class BaseDataSourceTest<N : NetworkApi> : BaseTest() {

    lateinit var mockLiveData: MutableLiveData<ApiResponse>
    lateinit var mockDisposable: Disposable
    lateinit var dataSource: N

    override fun setup() {
        super.setup()
        mockkObject(ApplicationProvider)
        every { ApplicationProvider.context } returns mockContext

        mockLiveData = mockk(relaxed = true)
        mockDisposable = mockk(relaxed = true)
    }
}