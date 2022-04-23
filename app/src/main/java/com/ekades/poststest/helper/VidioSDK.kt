package com.ekades.poststest.helper

import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.coroutines.CoroutineContext

object VidioSDK : CoroutineScope {


    /**/
    private const val BASE_URL = "https://restcountries.com/v3.1/name"
    private val mJob: Job by lazy { Job() }
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    fun downloadEpisode(episodeId: Long, downloadStatus: (episodeId: Long, DownloadStatus) -> Unit) = launch {
        downloadStatus.invoke(episodeId, DownloadStatus.DOWNLOADING)

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("$BASE_URL/$episodeId")
            .get()
            .build()

        withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                downloadStatus.invoke(episodeId, DownloadStatus.DOWNLOAD_FINISH)
            } else {
                downloadStatus.invoke(episodeId, DownloadStatus.DOWNLOADING)
            }
        }
    }
}