package com.ekades.poststest.features.users.data.dataSource

import com.ekades.poststest.features.users.data.dataSource.interfaces.IUserApiClient
import com.ekades.poststest.lib.core.networkV2.BaseApiClient

object UserApiClient : BaseApiClient<IUserApiClient>(IUserApiClient::class.java)