package com.ekades.temandoa.features.prayerdetail

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.ekades.temandoa.R
import com.ekades.temandoa.features.prayerdetail.model.PrayerItem
import com.ekades.temandoa.lib.application.ui.CoreActivity
import com.ekades.temandoa.lib.core.ui.foundation.background.CornerBackgroundLarge
import com.ekades.temandoa.lib.core.ui.foundation.background.shadowSmallReverse
import com.ekades.temandoa.lib.core.ui.foundation.color.ColorPalette
import com.ekades.temandoa.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.temandoa.lib.ui.asset.extension.dp
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_prayer_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.textColor
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
        setupViewPager(prayers)
        indicatorBgView.shadowSmallReverse(CornerRadius.SMALL)
    }

    private fun setupViewPager(prayers: List<PrayerItem>) {
        val modalAdapter = PrayerDetailAdapter(data = prayers)
        viewPager.adapter = modalAdapter
        vpIndicator.setupWithViewPager(viewPager)
        viewPager.currentItem = viewModel.currentPosition
        setupActionPrevious(viewModel.currentPosition > 0)
        setupActionNext(viewModel.currentPosition < (prayers.size - 1))
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                setupActionPrevious(position > 0)
                setupActionNext(position < (prayers.size - 1))
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun setupActionPrevious(isEnable: Boolean) {
        val color = if (isEnable) {
            ColorPalette.BRAND
        } else {
            ColorPalette.MERCURY
        }

        tvPrevious.textColor = color
        bgActionPrevious.background = CornerBackgroundLarge().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1.dp(), color)
        }
        bgActionPrevious.setOnClickListener {
            if (isEnable) {
                onClickPrevious()
            }
        }
    }

    private fun onClickPrevious() {
        viewPager.currentItem = viewPager.currentItem - 1
    }

    private fun setupActionNext(isEnable: Boolean) {
        val color = if (isEnable) {
            ColorPalette.BRAND
        } else {
            ColorPalette.MERCURY
        }

        tvNext.textColor = color
        bgActionNext.background = CornerBackgroundLarge().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1.dp(), color)
        }
        bgActionNext.setOnClickListener {
            if (isEnable) {
                onClickNext()
            }
        }
    }

    private fun onClickNext() {
        viewPager.currentItem = viewPager.currentItem + 1
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