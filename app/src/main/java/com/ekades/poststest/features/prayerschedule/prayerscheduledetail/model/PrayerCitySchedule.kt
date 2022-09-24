package com.ekades.poststest.features.prayerschedule.prayerscheduledetail.model

data class PrayerCitySchedule(
    val id: String,
    val lokasi: String,
    val daerah: String,
    val jadwal: PrayerSchedule
) {
    fun getScheduleItems(): List<ScheduleItem> {
        return arrayListOf(
            ScheduleItem(name = "Imsak", time = jadwal.imsak),
            ScheduleItem(name = "Subuh", time = jadwal.subuh),
            ScheduleItem(name = "Terbit", time = jadwal.terbit),
            ScheduleItem(name = "Dhuha", time = jadwal.dhuha),
            ScheduleItem(name = "Dzuhur", time = jadwal.dzuhur),
            ScheduleItem(name = "Ashar", time = jadwal.ashar),
            ScheduleItem(name = "Maghrib", time = jadwal.maghrib),
            ScheduleItem(name = "Isya", time = jadwal.isya)
        )
    }
}
