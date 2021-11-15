package com.ekades.poststest.lib.core.network.responses

import com.ekades.poststest.lib.core.network.responses.constant.StatusApiResponse
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result

open class BaseApiResponse(
    val status: StatusApiResponse,
    val data: Pair<Response, Result<BaseResponse, FuelError>>?,
    val error: Throwable?
)