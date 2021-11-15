package com.ekades.poststest.lib.core.network

import androidx.lifecycle.MutableLiveData
import com.ekades.poststest.lib.core.network.responses.ApiResponse
import com.ekades.poststest.lib.core.network.responses.ApiResponse.Companion.getErrorMessageBasedOnStatusCode
import com.ekades.poststest.lib.core.network.utils.constants.ApiMethod
import com.ekades.poststest.lib.core.network.utils.constants.StatusNetwork
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import org.json.JSONObject
import java.util.*

abstract class NetworkApi(
    method: ApiMethod
) : BaseApi<ApiResponse>(method) {

    var postParam = ""
    var formDataParam: List<Pair<String, Any?>> = listOf()

    override val headers: Map<String, String>?
        get() = generateAuthHeader()

    override var params: String = ""
        get() {
            val dataObject = JSONObject()
            dataObject.put("data", field)
            return dataObject.toString()
        }

    override var formData: List<Pair<String, Any?>>? = listOf()
        get() {
            val json = JSONObject()
            for (data in field!!) {
                json.put(data.first, data.second)
            }
            return listOf(Pair("data", json))
        }

    override fun doOnSubscribe(liveData: MutableLiveData<ApiResponse>) {
        liveData.value = ApiResponse.loading()
    }

    override fun subscribeStringRx(
        liveData: MutableLiveData<ApiResponse>,
        result: Pair<Response, Result<String, FuelError>>,
        throwable: Throwable?
    ) {
        if (throwable == null) {
            result.second.component2()?.apply {
                val errorMessage = getErrorMessageBasedOnStatusCode(this.response.statusCode)
                val errorThrowable = Throwable(errorMessage)
                liveData.value = ApiResponse.error(errorThrowable)
                return
            }

            if (result.second.component1()?.trim()?.isNotEmpty() == true) {
                liveData.value = ApiResponse.successV1(result.second.component1() ?: "")
            } else {
                val errorThrowable = Throwable(StatusNetwork.MESSAGE_WHOOPS_TRY_AGAIN)
                liveData.value = ApiResponse.error(errorThrowable)
                return
            }
        } else {
            liveData.value = ApiResponse.error(throwable)
        }
    }

    private fun generateApiKeyHeader(apiKey: String = ""): Map<String, String> {
        val resultHeader = HashMap<String, String>()
        if (apiKey.isNotBlank()) {
            resultHeader["x-api-key"] = apiKey
        }
        return resultHeader
    }

    private fun generateAuthHeader(): Map<String, String> {
        return HashMap()
    }
}