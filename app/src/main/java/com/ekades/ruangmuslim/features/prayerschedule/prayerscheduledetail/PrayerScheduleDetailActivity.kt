package com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekades.ruangmuslim.R
import com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.model.DateSelector
import com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.model.ScheduleItem
import com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.state.PrayerScheduleMonthlyVS
import com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.view.DateSelectorItemCV
import com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.view.ScheduleItemCV
import com.ekades.ruangmuslim.features.prayerschedule.searchcity.SearchCityActivity
import com.ekades.ruangmuslim.features.prayerschedule.searchcity.SearchCityActivity.Companion.EXTRA_CITY_ID
import com.ekades.ruangmuslim.features.prayerschedule.searchcity.SearchCityActivity.Companion.EXTRA_CITY_NAME
import com.ekades.ruangmuslim.lib.application.ui.CoreActivity
import com.ekades.ruangmuslim.lib.core.ui.extension.*
import com.ekades.ruangmuslim.lib.core.ui.foundation.background.CornerBackgroundLarge
import com.ekades.ruangmuslim.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.ruangmuslim.lib.core.ui.foundation.color.ColorPalette
import com.ekades.ruangmuslim.lib.core.ui.foundation.component.Component
import com.ekades.ruangmuslim.lib.core.ui.foundation.component.Rectangle
import com.ekades.ruangmuslim.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.ruangmuslim.lib.core.ui.foundation.container.LinearContainer
import com.ekades.ruangmuslim.lib.core.ui.foundation.spacing.Spacing
import com.ekades.ruangmuslim.lib.ui.asset.attributes.IconSize
import com.ekades.ruangmuslim.lib.ui.asset.extension.dp
import com.ekades.ruangmuslim.lib.ui.asset.icon.BasicIcon
import com.ekades.ruangmuslim.lib.ui.component.loading.RectangleSkeletonCV
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_prayer_schedule_detail.*
import kotlinx.android.synthetic.main.activity_prayer_schedule_detail.appBar
import kotlinx.android.synthetic.main.activity_prayer_schedule_detail.mainContentView
import kotlinx.android.synthetic.main.activity_prayer_schedule_detail.rvPrayerTime
import kotlinx.android.synthetic.main.activity_prayer_schedule_detail.toolbarCV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class PrayerScheduleDetailActivity :
    CoreActivity<PrayerScheduleDetailViewModel>(PrayerScheduleDetailViewModel::class) {

    private var cityId = ""
    private var cityName = ""

    private val prayerTimeAdapter by lazy {
        rvPrayerTime?.linearLayoutAdapter(this)
    }

    private val selectedDateAdapter by lazy {
        rvSelectedDate?.linearLayoutAdapter(this, RecyclerView.HORIZONTAL)
    }

    init {
        activityLayoutRes = R.layout.activity_prayer_schedule_detail
    }

    override fun render() = launch(Dispatchers.Main) {
        getIntentExtra()
        registerObserver()
        renderBgContentView()
        setupArrowIconDate()
        if (cityId.isNotBlank() && cityName.isNotBlank()) {
            setupAppBarListener(cityName)
            loadData()
        } else {
            openSearchCity()
        }
    }

    private fun loadData() {
        if (cityName.isBlank()) {
            clCity.gone()
        }
        viewModel.setCurrentMonth()
        viewModel.getPrayerScheduleMonthly(cityId)
    }

    private fun openSearchCity() {
        startActivityForResult(SearchCityActivity.newIntent(this), REQ_SEARCH_CITY)
    }

    private fun getIntentExtra() {
        cityId = intent.getStringExtra(EXTRA_CITY_ID) ?: ""
        cityName = intent.getStringExtra(EXTRA_CITY_NAME) ?: ""
    }

    private fun renderBgContentView() {
        mainContentView.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1, ColorPalette.WHITE)
        }

        shownMonthskeletonCV.bind {
            width = 400.dp()
            height = 100.dp()
        }
    }

    private fun setupArrowIconDate() {
        beforeIconCV.apply {
            isVisible = true
            bind {
                imageSize = IconSize.MEDIUM
                imageDrawable = BasicIcon.CHEVRON_RIGHT_BLACK
            }
            rotation = 180f
            beforeIconCV.setOnClickListener {
                viewModel.onBeforeDay(cityId)
            }
        }

        afterIconCV.apply {
            isVisible = true
            bind {
                imageSize = IconSize.MEDIUM
                imageDrawable = BasicIcon.CHEVRON_RIGHT_BLACK
            }
            afterIconCV.setOnClickListener {
                viewModel.onAfterDay(cityId)
            }
        }
    }

    private fun registerObserver() {
        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                is PrayerScheduleMonthlyVS.Success -> {
                    renderDate(viewState.month)
                    renderSelectionDateView(viewState.dateSelector)
                    renderPrayersScheduleTime(viewState.scheduleItemList)
                    viewState.scrollToIndex?.apply {
                        scrollToPosition(this)
                    }
                }
                is PrayerScheduleMonthlyVS.Error -> {
                    Toast.makeText(this, viewState.message, Toast.LENGTH_SHORT).show()
                }
                is PrayerScheduleMonthlyVS.ShowLoader -> {
                    showShownMonthLoader(viewState.showLoader)
                    if (viewState.showLoader) {
                        showLoadingSelectionDate()
                        showLoadingPrayerTime()
                    }
                }
            }
        })
    }

    private fun showShownMonthLoader(isVisible: Boolean) {
        shownMonthskeletonCV.isVisible = isVisible
        shownMonthTextView.isVisible = isVisible.not()
        afterIconCV.isVisible = isVisible.not()
        beforeIconCV.isVisible = isVisible.not()
    }

    private fun showLoadingSelectionDate() {
        val loadingComponents: MutableList<Component<*>> = mutableListOf()
        loadingComponents.addAll(getLoadingSelectionDateComponents())
        selectedDateAdapter?.setNewList(loadingComponents)
    }

    private fun getLoadingSelectionDateComponents(): MutableList<Component<RectangleSkeletonCV>> {
        val component: MutableList<Component<RectangleSkeletonCV>> = arrayListOf()
        for (i in 0 until 8) {
            component.add(LinearContainer.newComponent({
                RectangleSkeletonCV(this)
            }) {
                height = 80.dp()
                width = 100.dp()
                componentMargin = Rectangle(
                    vertical = Spacing.x8,
                    horizontal = Spacing.x8
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }

    private fun showLoadingPrayerTime() {
        val loadingComponents: MutableList<Component<*>> = mutableListOf()
        loadingComponents.addAll(getLoadingPrayerTimeComponents())
        prayerTimeAdapter?.setNewList(loadingComponents)
    }

    private fun getLoadingPrayerTimeComponents(): MutableList<Component<RectangleSkeletonCV>> {
        val component: MutableList<Component<RectangleSkeletonCV>> = arrayListOf()
        for (i in 0 until 8) {
            component.add(LinearContainer.newComponent({
                RectangleSkeletonCV(this)
            }) {
                height = 80.dp()
                componentMargin = Rectangle(
                    vertical = Spacing.x4,
                    horizontal = Spacing.x0
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }

    private fun setupAppBarListener(title: String) {
        tvCity.text = title.capitalizeWords
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                clCity.alpha = 0F
                showToolbar(title)
            } else {
                clCity.alpha = 1F
                showToolbar(title, false)
            }
        })

        clCity.visible()
        clCity.background = CornerBackgroundLarge().apply {
            setColor(ColorPalette.NARVIK)
            setStroke(1, ColorPalette.BRAND)
        }
        icPlace.bind {
            imageSize = IconSize.MEDIUM
            imageDrawable = R.drawable.ic_place
        }
        tvChangeCity.setOnClickListener {
            openSearchCity()
        }
    }

    private fun showToolbar(title: String, isVisible: Boolean = true) {
        toolbarCV.bind {
            onClickBackListener = {
                onBackPressed()
            }
            toolbarTitle = if (isVisible) {
                title.capitalizeWords
            } else {
                null
            }
        }
    }

    private fun renderDate(date: String) {
        shownMonthTextView.text = date
    }

    private fun renderSelectionDateView(dateSelectors: ArrayList<DateSelector>) {
        val components: MutableList<Component<*>> = dateSelectors.mapIndexed { _, item ->
            ConstraintContainer.newComponent({
                DateSelectorItemCV(this)
            }) {
                dateSelector = item
                onSelectedDateListener = {
                    viewModel.updateSelectedDate(it)
                }
            }.setIdentifier("${item.day}${item.date}${item.requestDate}${item.isSelected}")
        }.toMutableList()

        selectedDateAdapter?.diffCalculateAdapter(components)
    }

    private fun renderPrayersScheduleTime(scheduleItems: List<ScheduleItem>) {
        val components: MutableList<Component<*>> = scheduleItems.mapIndexed { _, item ->
            ConstraintContainer.newComponent({
                ScheduleItemCV(this)
            }) {
                scheduleItem = item
            }.setIdentifier("${item.name}${item.time}")
        }.toMutableList()

        prayerTimeAdapter?.diffCalculateAdapter(components)
    }

    private fun scrollToPosition(index: Int) {
        (rvSelectedDate.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            index,
            0
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_SEARCH_CITY) {
            if (resultCode == RESULT_OK) {
                val extraCityId = data?.getStringExtra(EXTRA_CITY_ID) ?: ""
                val extraCityName = data?.getStringExtra(EXTRA_CITY_NAME) ?: ""

                if (extraCityId.isNotBlank() && extraCityName.isNotBlank()) {
                    if (cityId != extraCityId) {
                        cityId = extraCityId
                        cityName = extraCityName
                        setupAppBarListener(cityName)
                        viewModel.clearScheduleDetail()
                        loadData()
                    }
                }
            } else if (cityId.isBlank()) {
                finish()
            }
        }
    }

    companion object {
        private const val REQ_SEARCH_CITY = 77

        @JvmStatic
        fun newIntent(context: Context?, cityId: String? = null, cityName: String? = null): Intent {
            return Intent(context, PrayerScheduleDetailActivity::class.java).apply {
                if (cityId?.isNotBlank() == true && cityName?.isNotBlank() == true) {
                    putExtra(EXTRA_CITY_ID, cityId)
                    putExtra(EXTRA_CITY_NAME, cityName)
                }
            }
        }
    }
}