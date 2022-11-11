package com.ekades.ruangmuslim.networksV2.prayerschedule.data.dataSource

import com.ekades.ruangmuslim.networksV2.prayerschedule.data.dataSource.interfaces.IPrayerScheduleApiClient
import com.ekades.ruangmuslim.lib.core.networkV2.BaseApiClient

object PrayerScheduleApiClient : BaseApiClient<IPrayerScheduleApiClient>(IPrayerScheduleApiClient::class.java)