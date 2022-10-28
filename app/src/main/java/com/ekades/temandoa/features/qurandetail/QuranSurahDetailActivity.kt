package com.ekades.temandoa.features.qurandetail

import android.content.Context
import android.content.Intent
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.ekades.temandoa.R
import com.ekades.temandoa.features.qurandetail.model.SurahDetail
import com.ekades.temandoa.features.qurandetail.view.QuranDetailItemCV
import com.ekades.temandoa.features.quranlist.model.Surah
import com.ekades.temandoa.lib.application.ui.CoreActivity
import com.ekades.temandoa.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.temandoa.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.temandoa.lib.core.ui.extension.newComponent
import com.ekades.temandoa.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.temandoa.lib.core.ui.foundation.color.ColorPalette
import com.ekades.temandoa.lib.core.ui.foundation.component.Component
import com.ekades.temandoa.lib.core.ui.foundation.component.Rectangle
import com.ekades.temandoa.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.temandoa.lib.core.ui.foundation.container.LinearContainer
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing
import com.ekades.temandoa.lib.ui.asset.extension.dp
import com.ekades.temandoa.lib.ui.component.loading.RectangleSkeletonCV
import kotlinx.android.synthetic.main.activity_quran_surah_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuranSurahDetailActivity :
    CoreActivity<QuranSurahDetailViewModel>(QuranSurahDetailViewModel::class) {

    private val adapter by lazy {
        rvQuranSurah?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_quran_surah_detail
    }

    override fun render() = launch(Dispatchers.Main) {
        viewModel.processIntent(intent)
        showToolbar()
        renderBgContentView()
        registerObserver()
        setupRecyclerView()
        viewModel.getQuranSurahDetail()
    }

    private fun showToolbar() {
        toolbarCV.bind {
            onClickBackListener = {
                onBackPressed()
            }
            toolbarTitle = viewModel.surah?.nama.orEmpty()
        }
    }

    private fun renderBgContentView() {
        mainContentView.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1, ColorPalette.WHITE)
        }
    }

    private fun registerObserver() {
        viewModel.shownQuranSurahDetail.observe(this, Observer {
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

    private fun setupRecyclerView() {
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight &&
                    scrollY > oldScrollY
                ) {
                    viewModel.onLoadMore()
                }
            }
        }
    }

    private fun renderList(surahDetailSections: List<SurahDetail>) {
        val components: MutableList<Component<*>> = surahDetailSections.mapIndexed { i, item ->
            ConstraintContainer.newComponent({
                QuranDetailItemCV(this)
            }) {
                surahDetail = item
                bgColor = i.getBgColor()
            }.setIdentifier(item.id)
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun Int.getBgColor(): Int {
        return if (this % 2 == 0) ColorPalette.WHITE else ColorPalette.NARVIK
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

    companion object {
        const val EXTRA_SURAH = "EXTRA_SURAH"

        @JvmStatic
        fun newIntent(context: Context?, surah: Surah): Intent {
            return Intent(context, QuranSurahDetailActivity::class.java).apply {
                putExtra(EXTRA_SURAH, surah)
            }
        }
    }
}