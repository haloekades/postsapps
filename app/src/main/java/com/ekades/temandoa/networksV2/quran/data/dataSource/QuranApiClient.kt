package com.ekades.temandoa.networksV2.quran.data.dataSource

import com.ekades.temandoa.networksV2.quran.data.dataSource.interfaces.IQuranApiClient
import com.ekades.temandoa.lib.core.networkV2.BaseApiClient

object QuranApiClient : BaseApiClient<IQuranApiClient>(IQuranApiClient::class.java)