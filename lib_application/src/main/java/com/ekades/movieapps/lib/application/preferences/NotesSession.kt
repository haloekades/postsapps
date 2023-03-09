package com.ekades.movieapps.lib.application.preferences

import android.content.Context

object NotesSession {
    private val pref: SharedPreferenceBuilder by lazy {
        SharedPreferenceBuilder(Tags.NOTES_PREF_TAG, Context.MODE_PRIVATE)
    }

    fun clearSession() {
        pref.getSharedPreference().edit().clear().commit()
    }

    private const val KEY_NOTES = "key_notes"

    var notesValue: String
        get() = pref.getStringPref(KEY_NOTES)
        set(value) = pref.setStringPref(KEY_NOTES, value)
}