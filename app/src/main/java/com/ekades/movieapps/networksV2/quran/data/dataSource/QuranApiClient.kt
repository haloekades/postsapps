package com.ekades.movieapps.networksV2.quran.data.dataSource

import com.ekades.movieapps.networksV2.quran.data.dataSource.interfaces.IQuranApiClient
import com.ekades.movieapps.lib.core.networkV2.BaseApiClient

object QuranApiClient : BaseApiClient<IQuranApiClient>(IQuranApiClient::class.java)