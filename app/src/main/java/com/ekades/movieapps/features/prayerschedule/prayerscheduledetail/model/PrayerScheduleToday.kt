package com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model

import com.google.gson.Gson
import java.util.*

data class PrayerScheduleToday(
    var id: String,
    var lokasi: String,
    var daerah: String?,
    var jadwal: PrayerSchedule?,
    var requestDate: String? = null
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    fun showPrayerTime(): String {
        return if (isBefore(jadwal?.subuh.convertToCalendar())) {
            "Subuh ${jadwal?.subuh}"
        } else if (isBefore(jadwal?.dzuhur.convertToCalendar())) {
            "Dzuhur ${jadwal?.dzuhur}"
        } else if (isBefore(jadwal?.ashar.convertToCalendar())) {
            "Ashar ${jadwal?.ashar}"
        } else if (isBefore(jadwal?.maghrib.convertToCalendar())) {
            "Maghrib ${jadwal?.maghrib}"
        } else if (isBeforeIsya) {
            "Isya ${jadwal?.isya}"
        } else {
            "Subuh ${jadwal?.subuh}"
        }
    }

    val isBeforeIsya: Boolean
        get() = isBefore(jadwal?.isya.convertToCalendar())

    private fun isBefore(time: Calendar): Boolean {
        val dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        return c.before(time)
    }

    private fun String?.convertToCalendar(): Calendar {
        val splitTime = this?.split(":")
        val hour = splitTime?.getOrNull(0)?.toIntOrNull()
        val minute = splitTime?.getOrNull(1)?.toIntOrNull()


        val dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        if (hour != null && minute != null) {
            c.set(Calendar.HOUR_OF_DAY, hour)
            c.set(Calendar.MINUTE, minute)
            c.add(Calendar.MINUTE, MINUTE_TOLERANCE)
        }

        return c
    }

    companion object {
        private const val MINUTE_TOLERANCE = 7
    }
}
