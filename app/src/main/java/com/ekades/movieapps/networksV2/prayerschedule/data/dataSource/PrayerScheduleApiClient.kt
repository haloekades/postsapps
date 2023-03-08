package com.ekades.movieapps.networksV2.prayerschedule.data.dataSource

import com.ekades.movieapps.networksV2.prayerschedule.data.dataSource.interfaces.IPrayerScheduleApiClient
import com.ekades.movieapps.lib.core.networkV2.BaseApiClient

object PrayerScheduleApiClient : BaseApiClient<IPrayerScheduleApiClient>(IPrayerScheduleApiClient::class.java)