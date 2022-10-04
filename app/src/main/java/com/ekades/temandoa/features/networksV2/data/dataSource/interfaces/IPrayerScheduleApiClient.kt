package com.ekades.temandoa.features.networksV2.data.dataSource.interfaces

import com.ekades.temandoa.features.networksV2.data.dataSource.response.PrayerCityScheduleResponse
import com.ekades.temandoa.features.networksV2.data.dataSource.response.PrayerScheduleMonthlyResponse
import com.ekades.temandoa.features.prayerschedule.searchcity.model.CityItem
import retrofit2.http.GET
import retrofit2.http.Path

interface IPrayerScheduleApiClient {
    @GET("sholat/kota/semua")
    suspend fun getAllCity(): List<CityItem>

    @GET("sholat/jadwal/{city_id}/{year}/{month}/{day}")
    suspend fun getPrayerScheduleToday(
        @Path("city_id") cityId: String,
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day: String
    ): PrayerCityScheduleResponse

    @GET("sholat/jadwal/{city_id}/{year}/{month}")
    suspend fun getPrayerScheduleMonthly(
        @Path("city_id") cityId: String,
        @Path("year") year: String,
        @Path("month") month: String
    ): PrayerScheduleMonthlyResponse
}