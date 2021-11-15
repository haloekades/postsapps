package com.ekades.poststest.ui.photo

import android.content.Intent
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.models.Photo
import com.ekades.poststest.ui.photo.PhotoDetailActivity.Companion.EXTRA_PHOTO

class PhotoDetailViewModel : BaseViewModel() {

    var photo: Photo? = null

    fun processIntent(intent: Intent) {
        if (intent.hasExtra(EXTRA_PHOTO)) {
            photo = intent.getParcelableExtra(EXTRA_PHOTO)
        }
    }
}