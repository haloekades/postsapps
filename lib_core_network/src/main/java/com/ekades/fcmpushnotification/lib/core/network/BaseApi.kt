package com.ekades.fcmpushnotification.lib.core.network

import androidx.lifecycle.MutableLiveData
import com.ekades.fcmpushnotification.lib.core.network.interfaces.ListUrls
import com.ekades.fcmpushnotification.lib.core.network.responses.BaseApiResponse
import com.ekades.fcmpushnotification.lib.core.network.utils.constants.ApiMethod
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.rx.rx_responseString
import com.github.kittinunf.result.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

open class BaseApi<T : BaseApiResponse>(
    open val method: ApiMethod
) {

    open val headers: Map<String, String>? = null
    open var basePath: String = ListUrls.BASE_URL
    open var params: String = ""
    open var formData: List<Pair<String, Any?>>? = null
    open var path: String = ""
    var filesToUpload: ArrayList<DataPart> = arrayListOf()
    private val timeoutMillis = 30000

    fun execute(liveData: MutableLiveData<T>): Disposable {
        return when (method) {
            ApiMethod.DELETE -> delete(liveData)
            ApiMethod.POST -> post(liveData)
            ApiMethod.PUT -> put(liveData)
            ApiMethod.UPLOAD -> upload(liveData)
            ApiMethod.PATCH -> patch(liveData)
            else -> get(liveData)
        }
    }

    private fun get(liveData: MutableLiveData<T>): Disposable {
        return executeRx("$basePath/$path".httpGet().header(generateHeader()), liveData)
    }

    private fun delete(liveData: MutableLiveData<T>): Disposable {
        return executeRx(
            "$basePath/$path".httpDelete().body(params).header(generateHeader()),
            liveData
        )
    }

    private fun delete(handler: (request: Request, response: Response, result: Result<Json, FuelError>) -> Unit) {
        "$basePath/$path".httpDelete()
            .timeout(timeoutMillis).timeoutRead(timeoutMillis)
            .body(params).header(generateHeader()).responseJson(handler)
    }

    private fun post(liveData: MutableLiveData<T>): Disposable {
        return executeRx(
            "$basePath/$path".httpPost().body(params).header(generateHeader()),
            liveData
        )
    }

    private fun post(handler: (request: Request, response: Response, result: Result<Json, FuelError>) -> Unit) {
        "$basePath/$path".httpPost()
            .timeout(timeoutMillis).timeoutRead(timeoutMillis)
            .body(params).header(generateHeader()).responseJson(handler)
    }

    private fun put(liveData: MutableLiveData<T>): Disposable {
        return executeRx(
            "$basePath/$path".httpPut().body(params).header(generateHeader()),
            liveData
        )
    }

    private fun put(handler: (request: Request, response: Response, result: Result<Json, FuelError>) -> Unit) {
        "$basePath/$path".httpPut()
            .timeout(timeoutMillis).timeoutRead(timeoutMillis)
            .body(params).header(generateHeader()).responseJson(handler)
    }

    private fun upload(liveData: MutableLiveData<T>): Disposable {
        return executeRx(
            "$basePath/$path".httpUpload(Method.POST, formData)
                .header(generateHeader())
                .dataParts { _, _ -> filesToUpload }, liveData
        )
    }

    private fun upload(handler: (request: Request, response: Response, result: Result<Json, FuelError>) -> Unit) {
        "$basePath/$path".httpUpload(Method.POST, formData)
            .timeout(timeoutMillis).timeoutRead(timeoutMillis)
            .header(generateHeader()).dataParts { _, _ ->
                filesToUpload
            }
            .responseJson(handler)
    }

    private fun patch(liveData: MutableLiveData<T>): Disposable {
        return executeRx(
            "$basePath/$path".httpPatch().body(params).header(generateHeader()),
            liveData
        )
    }

    private fun generateHeader(): Map<String, String> {
        return when (method) {
            ApiMethod.GET -> appendHeaderWithAcceptJson()
            ApiMethod.DELETE -> appendHeaderWithJsonSpecific()
            ApiMethod.POST -> appendHeaderWithJsonSpecific()
            ApiMethod.PUT -> appendHeaderWithJsonSpecific()
            ApiMethod.UPLOAD -> appendHeaderWithMultipartFormType()
            ApiMethod.PATCH -> appendHeaderWithJsonSpecific()
            else -> headers ?: mapOf()
        }
    }

    private fun appendHeaderWithJsonSpecific(): Map<String, String> {
        val newHeader = appendHeaderWithJsonContentType()
        newHeader.plus(appendHeaderWithAcceptJson())
        return newHeader
    }

    private fun appendHeaderWithJsonContentType(): Map<String, String> {
        val jsonSpecificMap = mapOf("Content-Type" to "application/json")
        return headers?.plus(jsonSpecificMap) ?: jsonSpecificMap
    }

    private fun appendHeaderWithAcceptJson(): Map<String, String> {
        val acceptJsonMap = mapOf("Accept" to "application/json")
        return headers?.plus(acceptJsonMap) ?: acceptJsonMap
    }

    private fun appendHeaderWithMultipartFormType(): Map<String, String> {
        val multipartFormSpecificMap =
            mapOf("Content-Type" to "multipart/form-data; boundary=${System.currentTimeMillis()}")
        return headers?.plus(multipartFormSpecificMap) ?: multipartFormSpecificMap
    }

    private fun executeRx(request: Request, liveData: MutableLiveData<T>): Disposable {
        return if (isRunningOnTestEnvironment()) {
            CompositeDisposable()
        } else {
            request.timeout(timeoutMillis)
                .timeoutRead(timeoutMillis)
                .rx_responseString()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { doOnSubscribe(liveData) }
                .subscribe { result, throwable ->
                    subscribeStringRx(liveData, result, throwable)
                }
        }
    }

    open fun doOnSubscribe(liveData: MutableLiveData<T>) {}

    open fun subscribeStringRx(
        liveData: MutableLiveData<T>,
        result: Pair<Response, Result<String, FuelError>>,
        throwable: Throwable?
    ) {
    }

    @Synchronized
    private fun isRunningOnTestEnvironment(): Boolean {
        if (BuildConfig.DEBUG) {
            return try {
                Class.forName("androidx.test.espresso.Espresso")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }

        return false
    }
}