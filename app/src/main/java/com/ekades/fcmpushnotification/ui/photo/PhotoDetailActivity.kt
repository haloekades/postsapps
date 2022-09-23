package com.ekades.fcmpushnotification.ui.photo

import android.app.Activity
import android.content.Intent
import com.ekades.fcmpushnotification.R
import com.ekades.fcmpushnotification.lib.application.ui.CoreActivity
import com.ekades.fcmpushnotification.lib.core.ui.extension.loadImageUrlWithPlaceHolder
import com.ekades.fcmpushnotification.lib.core.ui.foundation.color.ColorPalette
import com.ekades.fcmpushnotification.models.Photo
import kotlinx.android.synthetic.main.activity_photo_detail.*
import kotlinx.android.synthetic.main.activity_photo_detail.toolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.backgroundColor

class PhotoDetailActivity : CoreActivity<PhotoDetailViewModel>(PhotoDetailViewModel::class) {

    init {
        activityLayoutRes = R.layout.activity_photo_detail
    }

    override fun render() = launch(Dispatchers.Main) {
        setupToolbarView()
        viewModel.processIntent(intent)
        photoTitleTextView.text = viewModel.photo?.title
        zoomImageView.loadImageUrlWithPlaceHolder(
            "${viewModel.photo?.url}.png"
        )
    }

    private fun setupToolbarView() {
        toolbar.title = getString(R.string.title_photo_detail)
        toolbar.setTitleTextColor(ColorPalette.WHITE)
        toolbar.backgroundColor = ColorPalette.BRAND
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    companion object {
        val EXTRA_PHOTO = "extra_photo"

        @JvmStatic
        fun newIntent(activity: Activity, photo: Photo): Intent {
            return Intent(activity, PhotoDetailActivity::class.java).apply {
                putExtra(EXTRA_PHOTO, photo)
            }
        }
    }
}