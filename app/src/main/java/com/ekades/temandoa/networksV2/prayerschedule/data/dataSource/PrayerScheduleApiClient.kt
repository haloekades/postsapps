package com.ekades.temandoa.networksV2.prayerschedule.data.dataSource

import com.ekades.temandoa.networksV2.prayerschedule.data.dataSource.interfaces.IPrayerScheduleApiClient
import com.ekades.temandoa.lib.core.networkV2.BaseApiClient

object PrayerScheduleApiClient : BaseApiClient<IPrayerScheduleApiClient>(IPrayerScheduleApiClient::class.java)