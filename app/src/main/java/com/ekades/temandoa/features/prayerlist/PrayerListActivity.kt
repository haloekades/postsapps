package com.ekades.temandoa.features.prayerlist

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.ekades.temandoa.R
import com.ekades.temandoa.features.prayerdetail.PrayerDetailActivity
import com.ekades.temandoa.features.prayerdetail.model.PrayerItem
import com.ekades.temandoa.features.prayerlist.view.PrayerListItemCV
import com.ekades.temandoa.lib.application.ui.CoreActivity
import com.ekades.temandoa.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.temandoa.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.temandoa.lib.core.ui.extension.newComponent
import com.ekades.temandoa.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.temandoa.lib.core.ui.foundation.color.ColorPalette
import com.ekades.temandoa.lib.core.ui.foundation.component.Component
import com.ekades.temandoa.lib.core.ui.foundation.container.ConstraintContainer
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_prayer_list.appBar
import kotlinx.android.synthetic.main.activity_prayer_list.mainContentView
import kotlinx.android.synthetic.main.activity_prayer_list.rvPrayerTime
import kotlinx.android.synthetic.main.activity_prayer_list.titleCollapsingToolbarTextView
import kotlinx.android.synthetic.main.activity_prayer_list.toolbarCV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class PrayerListActivity : CoreActivity<PrayerListViewModel>(PrayerListViewModel::class) {

    private val adapter by lazy {
        rvPrayerTime?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_prayer_list
    }

    override fun render() = launch(Dispatchers.Main) {
        registerObeserver()
        renderBgContentView()
        viewModel.processIntent(intent)
    }

    private fun renderBgContentView() {
        mainContentView.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.SNOW)
            setStroke(1, ColorPalette.WHITE)
        }
    }

    private fun registerObeserver() {
        viewModel.toolbarTitle.observe(this, Observer { toolbarTitle ->
            toolbarTitle?.apply {
                renderToolbar(this)
            }
        })

        viewModel.itemList.observe(this, Observer {
            it?.apply {
                renderList(this)
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

    private fun renderList(mainSections: List<PrayerItem>) {
        val components: MutableList<Component<*>> = mainSections.mapIndexed { i, item ->
            ConstraintContainer.newComponent({
                PrayerListItemCV(this)
            }) {
                section = item
                onItemClickListener = {
                    openPrayerDetail(i)
                }
            }.setIdentifier(item.id.toString())
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun openPrayerDetail(index: Int) {
        startActivity(
            PrayerDetailActivity.newIntent(
                this,
                viewModel.toolbarTitle.value ?: "",
                index,
                viewModel.jsonFile
            )
        )
    }

    companion object {
        const val KEY_EXTRA_PRAYER_TYPE = "key_extra_prayer_type"
        const val KEY_EXTRA_JSON_FILE = "key_extra_name_json_file"

        @JvmStatic
        fun newIntent(context: Context?, prayerType: String, jsonFile: String): Intent {
            return Intent(context, PrayerListActivity::class.java).apply {
                putExtra(KEY_EXTRA_PRAYER_TYPE, prayerType)
                putExtra(KEY_EXTRA_JSON_FILE, jsonFile)
            }
        }
    }
}