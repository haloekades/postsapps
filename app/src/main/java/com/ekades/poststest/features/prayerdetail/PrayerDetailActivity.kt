package com.ekades.poststest.features.prayerdetail

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.ekades.poststest.R
import com.ekades.poststest.features.prayerdetail.model.PrayerItem
import com.ekades.poststest.lib.application.ui.CoreActivity
import com.ekades.poststest.lib.core.ui.foundation.background.shadowSmallReverse
import com.ekades.poststest.lib.core.ui.foundation.corner.CornerRadius
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_prayer_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class PrayerDetailActivity : CoreActivity<PrayerDetailViewModel>(PrayerDetailViewModel::class) {

    init {
        activityLayoutRes = R.layout.activity_prayer_detail
    }

    override fun render() = launch(Dispatchers.Main) {
        registerObeserver()
        viewModel.processIntent(intent)
    }

    private fun registerObeserver() {
        viewModel.toolbarTitle.observe(this, Observer { toolbarTitle ->
            toolbarTitle?.apply {
                renderToolbar(this)
            }
        })

        viewModel.itemList.observe(this, Observer {
            it?.apply {
                renderPrayerDetail(this)
            }
        })
    }

    private fun renderToolbar(title: String) {
        showToolbar(title)
        setupAppBarListener(title)
    }

    private fun setupAppBarListener(title: String) {
        titleCollapsingToolbarTextView.text = title
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                titleCollapsingToolbarTextView.alpha = 0F
                showToolbar(title)
            } else {
                titleCollapsingToolbarTextView.alpha = 1F
                showToolbar(title, false)
            }
        })
    }

    private fun showToolbar(title: String, isVisible: Boolean = true) {
        toolbarCV.bind {
            onClickBackListener = {
                onBackPressed()
            }
            toolbarTitle = if (isVisible) {
                title
            } else {
                null
            }
        }
    }

    private fun renderPrayerDetail(prayers: List<PrayerItem>) {
        val modalAdapter = PrayerDetailAdapter(data = prayers)
        viewPager.adapter = modalAdapter
        vpIndicator.setupWithViewPager(viewPager)
        viewPager.currentItem = viewModel.currentPosition
        indicatorBgView.shadowSmallReverse(CornerRadius.SMALL)
    }

    companion object {
        const val KEY_EXTRA_PRAYER_TYPE = "key_extra_prayer_type"
        const val KEY_EXTRA_POSITION = "key_extra_position"
        const val KEY_EXTRA_JSON_FILE = "key_extra_name_json_file"

        @JvmStatic
        fun newIntent(
            context: Context?, prayerType: String, position: Int = 0, jsonFile: String
        ): Intent {
            return Intent(context, PrayerDetailActivity::class.java).apply {
                putExtra(KEY_EXTRA_PRAYER_TYPE, prayerType)
                putExtra(KEY_EXTRA_POSITION, position)
                putExtra(KEY_EXTRA_JSON_FILE, jsonFile)
            }
        }
    }
}