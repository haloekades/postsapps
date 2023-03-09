package com.ekades.movieapps.features.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ekades.movieapps.R
import com.ekades.movieapps.lib.application.preferences.NotesSession
import kotlinx.android.synthetic.main.activity_notes.*

class NotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        etNotes.setText(NotesSession.notesValue)

        btnSave.setOnClickListener {
            NotesSession.notesValue = etNotes.text.toString()
        }
    }
}