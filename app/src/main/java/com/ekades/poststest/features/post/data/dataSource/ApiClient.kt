package com.ekades.poststest.features.post.data.dataSource

import com.ekades.poststest.features.post.data.dataSource.interfaces.IPostApiClient
import com.ekades.poststest.lib.core.networkV2.BaseApiClient

object PostApiClient : BaseApiClient<IPostApiClient>(IPostApiClient::class.java)