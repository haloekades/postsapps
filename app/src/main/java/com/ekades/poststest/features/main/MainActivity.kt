package com.ekades.poststest.features.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.ekades.poststest.R
import com.ekades.poststest.features.main.model.MainSection
import com.ekades.poststest.features.main.view.MainItemCV
import com.ekades.poststest.features.prayerdetail.PrayerDetailActivity
import com.ekades.poststest.features.prayerlist.PrayerListActivity
import com.ekades.poststest.features.prayerschedule.searchcity.SearchCityActivity
import com.ekades.poststest.lib.application.ui.CoreActivity
import com.ekades.poststest.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.poststest.lib.core.ui.extension.gridLayoutAdapter
import com.ekades.poststest.lib.core.ui.extension.newComponent
import com.ekades.poststest.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.poststest.lib.core.ui.foundation.color.ColorPalette
import com.ekades.poststest.lib.core.ui.foundation.component.Component
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : CoreActivity<MainViewModel>(MainViewModel::class) {

    private val adapter by lazy {
        mainRecyclerView?.gridLayoutAdapter(this, 2)
    }

    init {
        activityLayoutRes = R.layout.activity_main
    }

    override fun render() = launch(Dispatchers.Main) {
        registerObeserver()
        renderToolbar()
        renderBgContentView()
        viewModel.getSectionJson()
    }

    private fun registerObeserver() {
        viewModel.mainItemList.observe(this, Observer {
            it?.apply {
                renderMainList(this)
            }
        })
    }

    private fun renderToolbar() {
        showToolbar()
        setupAppBarListener()
    }

    private fun setupAppBarListener() {
        titleCollapsingToolbarTextView.text = getString(R.string.app_name)
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                titleCollapsingToolbarTextView.alpha = 0F
                showToolbar()
            } else {
                titleCollapsingToolbarTextView.alpha = 1F
                showToolbar(false)
            }
        })
        addCity.setOnClickListener {
            startActivity(SearchCityActivity.newIntent(this))
        }
    }

    private fun showToolbar(isVisible: Boolean = true) {
        toolbarCV.bind {
            toolbarTitle = if (isVisible) {
                getString(R.string.app_name)
            } else {
                null
            }
        }
    }

    private fun renderBgContentView() {
        mainContentView.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.SNOW)
            setStroke(1, ColorPalette.WHITE)
        }
    }

    private fun renderMainList(mainSections: List<MainSection>) {
        val components: MutableList<Component<*>> = mainSections.map { item ->
            ConstraintContainer.newComponent({
                MainItemCV(this)
            }) {
                section = item
                onItemClickListener = { section ->
                    if (section.isShowDetail) {
                        openPrayerDetail(section)
                    } else {
                        openPrayerList(section)
                    }
                }
            }.setIdentifier(item.id.toString())
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun openPrayerDetail(section: MainSection) {
        section.jsonFile?.apply {
            startActivity(
                PrayerDetailActivity.newIntent(
                    this@MainActivity,
                    section.name, 0,
                    section.jsonFile
                )
            )
        }
    }

    private fun openPrayerList(section: MainSection) {
        section.jsonFile?.apply {
            startActivity(
                PrayerListActivity.newIntent(
                    this@MainActivity,
                    section.name,
                    section.jsonFile
                )
            )
        }
    }

    companion object {

        @JvmStatic
        fun newIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}