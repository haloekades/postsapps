package com.ekades.fcmpushnotification.ui.photo

import android.content.Intent
import com.ekades.fcmpushnotification.lib.application.viewmodel.BaseViewModel
import com.ekades.fcmpushnotification.models.Photo
import com.ekades.fcmpushnotification.ui.photo.PhotoDetailActivity.Companion.EXTRA_PHOTO

class PhotoDetailViewModel : BaseViewModel() {

    var photo: Photo? = null

    fun processIntent(intent: Intent) {
        if (intent.hasExtra(EXTRA_PHOTO)) {
            photo = intent.getParcelableExtra(EXTRA_PHOTO)
        }
    }
}