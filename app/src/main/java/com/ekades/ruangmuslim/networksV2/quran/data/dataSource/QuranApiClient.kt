package com.ekades.ruangmuslim.networksV2.quran.data.dataSource

import com.ekades.ruangmuslim.networksV2.quran.data.dataSource.interfaces.IQuranApiClient
import com.ekades.ruangmuslim.lib.core.networkV2.BaseApiClient

object QuranApiClient : BaseApiClient<IQuranApiClient>(IQuranApiClient::class.java)