package com.ekades.movieapps.features.mainmovie

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.ekades.movieapps.R
import com.ekades.movieapps.features.main.model.MainSection
import com.ekades.movieapps.features.main.model.MainSection.Companion.ID_MUROTTAL
import com.ekades.movieapps.features.main.model.MainSection.Companion.ID_QURAN
import com.ekades.movieapps.features.main.state.PrayerScheduleTodayVS
import com.ekades.movieapps.features.main.view.MainItemCV
import com.ekades.movieapps.features.murottal.MurottalListActivity
import com.ekades.movieapps.features.prayerdetail.PrayerDetailActivity
import com.ekades.movieapps.features.prayerlist.PrayerListActivity
import com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.PrayerScheduleDetailActivity
import com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model.PrayerScheduleToday
import com.ekades.movieapps.features.prayerschedule.searchcity.SearchCityActivity
import com.ekades.movieapps.features.prayerschedule.searchcity.SearchCityActivity.Companion.EXTRA_CITY_ID
import com.ekades.movieapps.features.prayerschedule.searchcity.SearchCityActivity.Companion.EXTRA_CITY_NAME
import com.ekades.movieapps.features.quranlist.QuranSurahListActivity
import com.ekades.movieapps.lib.application.ui.CoreActivity
import com.ekades.movieapps.lib.core.ui.extension.*
import com.ekades.movieapps.lib.core.ui.foundation.background.CornerBackgroundLarge
import com.ekades.movieapps.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.movieapps.lib.core.ui.foundation.color.ColorPalette
import com.ekades.movieapps.lib.core.ui.foundation.component.Component
import com.ekades.movieapps.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.movieapps.lib.ui.asset.attributes.IconSize
import com.ekades.movieapps.lib.ui.asset.extension.dp
import com.ekades.movieapps.lib.ui.component.misc.DividerCV
import com.ekades.movieapps.utils.MovieUtils
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.appBar
import kotlinx.android.synthetic.main.activity_main.mainContentView
import kotlinx.android.synthetic.main.activity_main.rvPrayerTime
import kotlinx.android.synthetic.main.activity_main.toolbarCV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainMovieActivity : CoreActivity<MainMovieViewModel>(MainMovieViewModel::class) {

    private val adapter by lazy {
        rvPrayerTime?.gridLayoutAdapter(this, 2)
    }

    init {
        activityLayoutRes = R.layout.activity_main
    }

    override fun render() = launch(Dispatchers.Main) {
        registerObeserver()
        renderToolbar()
        renderBgContentView()
        initClPrayerTimeView()
        viewModel.checkPrayerTime()
        viewModel.getSectionJson()
//        viewModel.getMovieDBFromJson()
        MovieUtils.movieDB
    }

    private fun registerObeserver() {
        viewModel.mainItemList.observe(this, Observer {
            it?.apply {
                renderMainList(this)
            }
        })

        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                is PrayerScheduleTodayVS.ShowPrayerScheduleToday -> {
                    viewState.prayerScheduleToday.showLocationAndTime()
                }
                is PrayerScheduleTodayVS.Error -> {
                    Toast.makeText(this, viewState.message, Toast.LENGTH_SHORT).show()
                }
                is PrayerScheduleTodayVS.ShowLoader -> {
                    showLoaderPrayerTime(viewState.showLoader)
                }
            }
        })
    }

    private fun renderToolbar() {
        showToolbar()
        setupAppBarListener()
    }

    private fun PrayerScheduleToday.showLocationAndTime() {
        groupLocation.visible()
        setupClPrayerTimeClickListener(null)

        tvLocation.text = lokasi.capitalizeWords
        tvPrayerTime.text = showPrayerTime()

        dividerCV.bind { dividerStyle = DividerCV.DividerStyle.STRAIGHT }

        tvChangeCity.setOnClickListener {
            startActivityForResult(SearchCityActivity.newIntent(this@MainMovieActivity), REQ_CHANGE_CITY)
        }

        tvDetailPrayerTime.setOnClickListener {
            startActivityForResult(
                PrayerScheduleDetailActivity.newIntent(
                    this@MainMovieActivity,
                    cityId = id,
                    cityName = lokasi
                ),
                REQ_INPUT_LOCATION
            )
        }
    }

    private fun initClPrayerTimeView() {
        icLocation.bind {
            imageSize = IconSize.MEDIUM
            imageDrawable = R.drawable.ic_place
            imageTint = ColorPalette.EMPEROR
        }

        icClock.bind {
            imageSize = IconSize.MEDIUM
            imageDrawable = R.drawable.ic_clock
            imageTint = ColorPalette.EMPEROR
        }

        clPrayerTime.background = CornerBackgroundLarge().apply {
            setColor(ColorPalette.NARVIK)
            setStroke(1, ColorPalette.BRAND)
        }

        setupClPrayerTimeClickListener {
            startActivityForResult(PrayerScheduleDetailActivity.newIntent(this), REQ_INPUT_LOCATION)
        }
    }

    private fun setupClPrayerTimeClickListener(onClick: (() -> Unit)?) {
        clPrayerTime.setOnClickListener {
            onClick?.invoke()
        }
    }

    private fun showLoaderPrayerTime(isShow: Boolean) {
        if (isShow) {
            loaderPrayerTime.visible()
            loaderDetailPrayerTime.visible()
            tvPrayerTime.gone()
            tvDetailPrayerTime.gone()

            loaderPrayerTime.bind {
                width = 200.dp()
                height = 36.dp()
            }

            loaderDetailPrayerTime.bind {
                width = 100.dp()
                height = 36.dp()
            }
        } else {
            loaderPrayerTime.gone()
            loaderDetailPrayerTime.gone()
            tvPrayerTime.visible()
            tvDetailPrayerTime.visible()
        }
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
    }

    private fun showToolbar(isVisible: Boolean = true) {
        toolbarCV.bind {
            toolbarTitle = if (isVisible) {
                getToolbarText()
            } else {
                null
            }
        }
    }

    private fun getToolbarText(): String {
        return if (groupLocation.isVisible) {
            "${tvLocation.text} - ${tvPrayerTime.text}"
        } else {
            getString(R.string.app_name)
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
                    if (section.id == ID_QURAN) {
                        openQuranPage()
                    } else if (section.id == ID_MUROTTAL) {
                        openMurottalPage()
                    } else if (section.isShowDetail) {
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
                    this@MainMovieActivity,
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
                    this@MainMovieActivity,
                    section.name,
                    section.jsonFile
                )
            )
        }
    }

    private fun openQuranPage() {
        startActivity(QuranSurahListActivity.newIntent(this))
    }

    private fun openMurottalPage() {
        startActivity(MurottalListActivity.newIntent(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_INPUT_LOCATION) {
            viewModel.checkPrayerTime()
        } else if (requestCode == REQ_CHANGE_CITY && resultCode == RESULT_OK) {
            val cityId = data?.getStringExtra(EXTRA_CITY_ID) ?: ""
            val cityName = data?.getStringExtra(EXTRA_CITY_NAME) ?: ""

            if (cityId.isNotEmpty() && cityName.isNotEmpty()) {
                tvLocation.text = cityName.capitalizeWords
                viewModel.onChangeCity(cityId, cityName)
            }
        }
    }

    companion object {
        private const val REQ_INPUT_LOCATION = 2
        private const val REQ_CHANGE_CITY = 3

        @JvmStatic
        fun newIntent(context: Context?): Intent {
            return Intent(context, MainMovieActivity::class.java)
        }
    }
}