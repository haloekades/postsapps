package com.ekades.poststest.features.prayerschedule.searchcity

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.ekades.poststest.R
import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.PrayerScheduleDetailActivity
import com.ekades.poststest.features.prayerschedule.searchcity.model.CityItem
import com.ekades.poststest.features.prayerschedule.searchcity.view.CityListItemCV
import com.ekades.poststest.lib.application.ui.CoreActivity
import com.ekades.poststest.lib.core.ui.extension.*
import com.ekades.poststest.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.poststest.lib.core.ui.foundation.color.ColorPalette
import com.ekades.poststest.lib.core.ui.foundation.component.Component
import com.ekades.poststest.lib.core.ui.foundation.component.Rectangle
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.poststest.lib.core.ui.foundation.container.LinearContainer
import com.ekades.poststest.lib.core.ui.foundation.spacing.Spacing
import com.ekades.poststest.lib.ui.asset.extension.dp
import com.ekades.poststest.lib.ui.component.loading.RectangleSkeletonCV
import kotlinx.android.synthetic.main.activity_search_city.*
import kotlinx.android.synthetic.main.activity_search_city.mainContentView
import kotlinx.android.synthetic.main.activity_search_city.rvPrayerTime
import kotlinx.android.synthetic.main.activity_search_city.toolbarCV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchCityActivity : CoreActivity<SearchCityViewModel>(SearchCityViewModel::class) {

    private val adapter by lazy {
        rvPrayerTime?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_search_city
    }

    override fun render() = launch(Dispatchers.Main) {
        showToolbar()
        setupInputTextListener()
        registerObeserver()
        renderBgContentView()
        setupRecyclerView()
        viewModel.getAllCity()
    }

    private fun setupInputTextListener() {
        inputFieldCV.bind {
            inputHint = "Masukkan Nama Kota / Kabupaten"
            onAfterTextChangedListener = { text ->
                viewModel.onSearchCity(text)
            }
        }
    }

    private fun renderBgContentView() {
        mainContentView.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1, ColorPalette.WHITE)
        }
    }

    private fun registerObeserver() {
        viewModel.shownCityList.observe(this, Observer {
            it?.apply {
                renderList(this)
            }
        })

        viewModel.showLoading.observe(this, Observer { isShow ->
            if (isShow) {
                showLoadingView()
            }
        })
    }

    private fun showToolbar() {
        toolbarCV.bind {
            onClickBackListener = {
                onBackPressed()
            }
            toolbarTitle = "Jadwal Sholat"
        }
    }

    private fun setupRecyclerView() {
        rvPrayerTime.infiniteScrollListener(
            visibleThreshold = VISIBLE_THRESHOLD,
            loadMoreListener = {
                if (inputFieldCV.getInputText().isBlank()) {
                    viewModel.onLoadMore()
                }
            }
        )
    }

    private fun renderList(citySections: List<CityItem>) {
        val components: MutableList<Component<*>> = citySections.mapIndexed { i, item ->
            ConstraintContainer.newComponent({
                CityListItemCV(this)
            }) {
                city = item
                onItemClickListener = { cityId, cityName ->
                    onSelectedCity(cityId, cityName)
                }
            }.setIdentifier(item.id)
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun showLoadingView() {
        val loadingComponents: MutableList<Component<*>> = mutableListOf()
        loadingComponents.addAll(getLoadingPrayerTimeComponents())
        adapter?.setNewList(loadingComponents)
    }

    private fun getLoadingPrayerTimeComponents(): MutableList<Component<RectangleSkeletonCV>> {
        val component: MutableList<Component<RectangleSkeletonCV>> = arrayListOf()
        for (i in 0 until 10) {
            component.add(LinearContainer.newComponent({
                RectangleSkeletonCV(this)
            }) {
                height = 70.dp()
                componentMargin = Rectangle(
                    vertical = Spacing.x4,
                    horizontal = Spacing.x12
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }

    private fun onSelectedCity(cityId: String, cityName: String) {
        val intent = Intent()
        intent.putExtra(EXTRA_CITY_ID, cityId)
        intent.putExtra(EXTRA_CITY_NAME, cityName)
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_CITY_ID = "extra_city_id"
        const val EXTRA_CITY_NAME = "extra_city_name"
        const val VISIBLE_THRESHOLD = 20

        @JvmStatic
        fun newIntent(context: Context?): Intent {
            return Intent(context, SearchCityActivity::class.java)
        }
    }
}