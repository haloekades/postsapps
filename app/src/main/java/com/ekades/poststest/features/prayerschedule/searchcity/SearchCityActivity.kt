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
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import kotlinx.android.synthetic.main.activity_search_city.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchCityActivity : CoreActivity<SearchCityViewModel>(SearchCityViewModel::class) {

    private val adapter by lazy {
        mainRecyclerView?.linearLayoutAdapter(this)
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
        mainRecyclerView.infiniteScrollListener(
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
                onItemClickListener = { cityId ->
                    onSelectedCity(cityId)
                }
            }.setIdentifier(item.id)
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun onSelectedCity(cityId: String) {
        startActivity(PrayerScheduleDetailActivity.newIntent(this, cityId))
    }

    companion object {
        const val VISIBLE_THRESHOLD = 20

        @JvmStatic
        fun newIntent(context: Context?): Intent {
            return Intent(context, SearchCityActivity::class.java)
        }
    }
}