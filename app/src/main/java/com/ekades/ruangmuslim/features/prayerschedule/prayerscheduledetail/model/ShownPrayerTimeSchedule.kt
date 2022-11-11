package com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.model

import com.ekades.ruangmuslim.lib.core.ui.foundation.color.ColorPalette

data class ShownPrayerTimeSchedule(
    val jadwal: PrayerSchedule
) {
    fun getScheduleItems(): List<ScheduleItem> {
        return arrayListOf(
            ScheduleItem(name = "Imsak", time = jadwal.imsak, bgColor = ColorPalette.WHITE),
            ScheduleItem(name = "Subuh", time = jadwal.subuh, bgColor = ColorPalette.NARVIK),
            ScheduleItem(name = "Terbit", time = jadwal.terbit, bgColor = ColorPalette.WHITE),
            ScheduleItem(name = "Dhuha", time = jadwal.dhuha, bgColor = ColorPalette.NARVIK),
            ScheduleItem(name = "Dzuhur", time = jadwal.dzuhur, bgColor = ColorPalette.WHITE),
            ScheduleItem(name = "Ashar", time = jadwal.ashar, bgColor = ColorPalette.NARVIK),
            ScheduleItem(name = "Maghrib", time = jadwal.maghrib, bgColor = ColorPalette.WHITE),
            ScheduleItem(name = "Isya", time = jadwal.isya, bgColor = ColorPalette.NARVIK)
        )
    }
}
