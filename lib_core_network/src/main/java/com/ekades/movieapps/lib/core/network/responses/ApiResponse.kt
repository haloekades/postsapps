package com.ekades.movieapps.lib.core.network.responses

import com.ekades.movieapps.lib.core.network.responses.constant.StatusApiResponse
import com.ekades.movieapps.lib.core.network.utils.constants.StatusNetwork
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ApiResponse(
    status: StatusApiResponse,
    data: Pair<Response, Result<BaseResponse, FuelError>>?,
    error: Throwable?,
    var plainResponseV1: String? = null
) : BaseApiResponse(status, data, error) {

    inline fun <reified T> convertToObject(): T? {
        return try {
            Gson().fromJson<T>(plainResponseV1, object : TypeToken<T>() {}.type)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        fun loading(): ApiResponse {
            return ApiResponse(StatusApiResponse.LOADING, null, null)
        }

        fun successV1(response: String): ApiResponse {
            return ApiResponse(StatusApiResponse.SUCCESS, null, null, response)
        }

        fun error(error: Throwable?): ApiResponse {
            return ApiResponse(StatusApiResponse.ERROR, null, error)
        }

        fun getErrorMessageBasedOnStatusCode(statusCode: Int): String {
            return when (statusCode) {
                401, 330 -> StatusNetwork.MESSAGE_INVALID_TOKEN
                429 -> StatusNetwork.MESSAGE_TO_MANY_REQUEST
                503 -> StatusNetwork.MESSAGE_MAINTENANCE
                in 400..599 -> StatusNetwork.MESSAGE_WHOOPS_TRY_AGAIN
                -1 -> StatusNetwork.MESSAGE_SERVER_CLOSED
                else -> {
                    StatusNetwork.MESSAGE_CHECK_CONNECTION
                }
            }
        }
    }
}