package com.ekades.movieapps.features.quranlist

import android.content.Context
import android.content.Intent
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.ekades.movieapps.R
import com.ekades.movieapps.features.qurandetail.QuranSurahDetailActivity
import com.ekades.movieapps.features.quranlist.model.Surah
import com.ekades.movieapps.features.quranlist.view.QuranListItemCV
import com.ekades.movieapps.lib.application.ui.CoreActivity
import com.ekades.movieapps.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.movieapps.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.movieapps.lib.core.ui.extension.newComponent
import com.ekades.movieapps.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.movieapps.lib.core.ui.foundation.color.ColorPalette
import com.ekades.movieapps.lib.core.ui.foundation.component.Component
import com.ekades.movieapps.lib.core.ui.foundation.component.Rectangle
import com.ekades.movieapps.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.movieapps.lib.core.ui.foundation.container.LinearContainer
import com.ekades.movieapps.lib.core.ui.foundation.spacing.Spacing
import com.ekades.movieapps.lib.ui.asset.extension.dp
import com.ekades.movieapps.lib.ui.component.loading.RectangleSkeletonCV
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_quran_surah_list.*
import kotlinx.android.synthetic.main.activity_quran_surah_list.appBar
import kotlinx.android.synthetic.main.activity_quran_surah_list.nestedScrollView
import kotlinx.android.synthetic.main.activity_quran_surah_list.titleCollapsingToolbarTextView
import kotlinx.android.synthetic.main.activity_quran_surah_list.toolbarCV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class QuranSurahListActivity :
    CoreActivity<QuranSurahListViewModel>(QuranSurahListViewModel::class) {

    private val adapter by lazy {
        rvQuranSurah?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_quran_surah_list
    }

    override fun render() = launch(Dispatchers.Main) {
        setupAppBarListener()
        renderViewTopRound()
        registerObserver()
        setupRecyclerView()
        viewModel.getAllQuranSurah()
    }

    private fun showToolbar(isVisible: Boolean = true) {
        toolbarCV.bind {
            onClickBackListener = {
                onBackPressed()
            }
            toolbarTitle = if (isVisible) {
                getString(R.string.al_quran)
            } else {
                null
            }
        }
    }

    private fun setupAppBarListener() {
        titleCollapsingToolbarTextView.text = getString(R.string.al_quran)
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

    private fun renderViewTopRound() {
        viewTopRound.background = CornerBackroundTopMedium().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1, ColorPalette.WHITE)
        }
    }

    private fun registerObserver() {
        viewModel.shownQuranSurahList.observe(this, Observer {
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

    private fun renderList(surahSections: List<Surah>) = launch {
        val components: MutableList<Component<*>> = surahSections.mapIndexed { _, item ->
            ConstraintContainer.newComponent({
                QuranListItemCV(this@QuranSurahListActivity)
            }) {
                surah = item
                onItemClickListener = { surah ->
                    openSurahDetail(surah)
                }
            }.setIdentifier(item.namaLatin)
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun openSurahDetail(surah: Surah) {
        startActivity(QuranSurahDetailActivity.newIntent(this, surah))
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
                height = 130.dp()
                componentMargin = Rectangle(
                    vertical = Spacing.x4,
                    horizontal = Spacing.x8
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }

    companion object {
        @JvmStatic
        fun newIntent(context: Context?): Intent {
            return Intent(context, QuranSurahListActivity::class.java)
        }
    }
}