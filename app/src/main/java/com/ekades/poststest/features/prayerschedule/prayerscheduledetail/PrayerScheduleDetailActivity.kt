package com.ekades.poststest.features.prayerschedule.prayerscheduledetail

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ekades.poststest.R
import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.model.ScheduleItem
import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.state.PrayerScheduleTodayVS
import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.view.ScheduleItemCV
import com.ekades.poststest.lib.application.ui.CoreActivity
import com.ekades.poststest.lib.core.ui.extension.*
import com.ekades.poststest.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.poststest.lib.core.ui.foundation.color.ColorPalette
import com.ekades.poststest.lib.core.ui.foundation.component.Component
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_prayer_schedule_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class PrayerScheduleDetailActivity : CoreActivity<PrayerScheduleDetailViewModel>(PrayerScheduleDetailViewModel::class) {

    private var cityId = ""

    private val adapter by lazy {
        mainRecyclerView?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_prayer_schedule_detail
    }

    override fun render() = launch(Dispatchers.Main) {
        getIntentExtra()
        registerObserver()
        renderBgContentView()
        viewModel.getPrayerScheduleToday(cityId)
    }

    private fun getIntentExtra() {
        cityId = intent.getStringExtra(EXTRA_CITY_ID) ?: ""
    }

    private fun renderBgContentView() {
        mainContentView.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1, ColorPalette.WHITE)
        }
    }

    private fun registerObserver() {
        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                is PrayerScheduleTodayVS.Success -> {
                    val title = "${viewState.prayerCitySchedule.lokasi}, ${viewState.prayerCitySchedule.daerah}"
                    setupAppBarListener(title)
                    renderList(viewState.prayerCitySchedule.getScheduleItems())
                }
                is PrayerScheduleTodayVS.Error -> {
                    Toast.makeText(this, viewState.message, Toast.LENGTH_SHORT).show()
                }
                is PrayerScheduleTodayVS.ShowLoader -> {

                }
            }
        })
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

    private fun renderList(scheduleItems: List<ScheduleItem>) {
        val components: MutableList<Component<*>> = scheduleItems.mapIndexed { _, item ->
            ConstraintContainer.newComponent({
                ScheduleItemCV(this)
            }) {
                scheduleItem = item
            }.setIdentifier(item.name)
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun onSelectedCity(index: Int) {
//        startActivity(
//            PrayerDetailActivity.newIntent(
//                this,
//                viewModel.toolbarTitle.value ?: "",
//                index,
//                viewModel.jsonFile
//            )
//        )
    }

    companion object {
        private const val EXTRA_CITY_ID = "extra_city_id"

        @JvmStatic
        fun newIntent(context: Context?, cityId: String): Intent {
            return Intent(context, PrayerScheduleDetailActivity::class.java).apply {
                putExtra(EXTRA_CITY_ID, cityId)
            }
        }
    }
}