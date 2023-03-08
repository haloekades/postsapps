package com.ekades.movieapps.features.quranlist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Surah(
    val nomor: String,
    @SerializedName("nama_latin")
    val namaLatin: String,
    val nama: String,
    @SerializedName("jumlah_ayat")
    val jumlahAyat: Int,
    @SerializedName("tempat_turun")
    val type: String,
    val arti: String,
    val deskripsi: String,
    val audio: String,
    var isSelected: Boolean = false,
    var isPlaying: Boolean = false
) : Parcelable {

    val showType: String
        get() = getShownType()

    val isHideBismillah: Boolean
        get() = nomor == NOMOR_AL_FATIFAH || nomor == NOMOR_AL_TAUBAH

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

        const val NOMOR_AL_FATIFAH = "1"
        const val NOMOR_AL_TAUBAH = "9"
    }
}
