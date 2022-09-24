package com.ekades.poststest.features.networksV2.data.dataSource

import com.ekades.poststest.features.networksV2.data.dataSource.interfaces.IPrayerScheduleApiClient
import com.ekades.poststest.features.users.data.dataSource.interfaces.IUserApiClient
import com.ekades.poststest.lib.core.networkV2.BaseApiClient

object PrayerScheduleApiClient : BaseApiClient<IPrayerScheduleApiClient>(IPrayerScheduleApiClient::class.java)