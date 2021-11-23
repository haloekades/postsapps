package com.carlosgub.coroutines.features.books.data.datasource.rest

import com.ekades.poststest.features.post.data.dataSource.interfaces.IPostApiClient
import com.ekades.poststest.lib.core.networkV2.BaseApiClient

object PostApiClient : BaseApiClient<IPostApiClient>(IPostApiClient::class.java)