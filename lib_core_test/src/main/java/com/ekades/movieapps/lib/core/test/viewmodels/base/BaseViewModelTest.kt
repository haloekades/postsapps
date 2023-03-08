package com.ekades.movieapps.lib.core.test.viewmodels.base

import androidx.lifecycle.ViewModel
import com.ekades.movieapps.lib.core.network.responses.ApiResponse
import com.ekades.movieapps.lib.core.network.responses.BaseResponse
import com.ekades.movieapps.lib.core.network.responses.constant.StatusApiResponse
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before

abstract class BaseViewModelTest<VM: ViewModel> : BaseTest() {
    lateinit var mViewModel: VM
    lateinit var mockDisposable: CompositeDisposable

    private lateinit var mockStatusResponse: StatusApiResponse
    private lateinit var mockData: Pair<Response, Result<BaseResponse, FuelError>>
    private lateinit var mockThrowable: Throwable

    @Before
    override fun setup() {
        super.setup()
        mockStatusResponse = mockk(relaxed = true)
        mockData = mockk(relaxed = true)
        mockThrowable = mockk(relaxed = true)
        mockDisposable = mockk()
    }
    
    protected fun ApiResponse.toMockCustomResponse() =
            spyk(ApiResponse(this.status, this.data, this.error))
    
    protected fun mockResponse() =
            spyk(ApiResponse(mockStatusResponse, mockData, mockThrowable))
    
    protected fun mockLoadingResponse() =
            spyk(ApiResponse(StatusApiResponse.LOADING, mockData, mockThrowable))
    
    protected fun mockSuccessResponse() =
            spyk(ApiResponse(StatusApiResponse.SUCCESS, mockData, mockThrowable))
    
    protected fun mockErrorResponse() =
            spyk(ApiResponse(StatusApiResponse.ERROR, mockData, mockThrowable))
}