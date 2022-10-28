package com.ekades.temandoa.features.quranlist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Surah(
    val arti: String,
    val asma: String,
    val ayat: Int,
    val nama: String,
    val type: String,
    val urut: String,
    val audio: String,
    val nomor: String,
    val rukuk: String,
    val keterangan: String
) : Parcelable {

    val showType: String
        get() = getShownType()

    private fun getShownType(): String {
        return if (type.contains(TYPE_MEKAH)) {
            TYPE_MAKKIYAH
        } else if (type.contains(TYPE_MADINAH)) {
            TYPE_MADANIYAH
        } else {
            type
        }
    }

    companion object {
        private const val TYPE_MEKAH = "mekah"
        private const val TYPE_MADINAH = "madinah"
        private const val TYPE_MAKKIYAH = "Makkiyah"
        private const val TYPE_MADANIYAH = "Madaniyah"
    }
}
