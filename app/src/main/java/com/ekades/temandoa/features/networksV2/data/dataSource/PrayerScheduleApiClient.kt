package com.ekades.temandoa.features.networksV2.data.dataSource

import com.ekades.temandoa.features.networksV2.data.dataSource.interfaces.IPrayerScheduleApiClient
import com.ekades.temandoa.lib.core.networkV2.BaseApiClient

object PrayerScheduleApiClient : BaseApiClient<IPrayerScheduleApiClient>(IPrayerScheduleApiClient::class.java)