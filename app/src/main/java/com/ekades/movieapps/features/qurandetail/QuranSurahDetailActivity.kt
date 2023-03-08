package com.ekades.movieapps.features.qurandetail

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.ekades.movieapps.R
import com.ekades.movieapps.features.qurandetail.model.SurahDetail
import com.ekades.movieapps.features.qurandetail.view.QuranDetailItemCV
import com.ekades.movieapps.features.quranlist.model.Surah
import com.ekades.movieapps.lib.application.ui.CoreActivity
import com.ekades.movieapps.lib.core.ui.extension.*
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
import kotlinx.android.synthetic.main.activity_quran_surah_detail.*
import kotlinx.android.synthetic.main.activity_quran_surah_detail.appBar
import kotlinx.android.synthetic.main.activity_quran_surah_detail.nestedScrollView
import kotlinx.android.synthetic.main.activity_quran_surah_detail.rvQuranSurah
import kotlinx.android.synthetic.main.activity_quran_surah_detail.toolbarCV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class QuranSurahDetailActivity :
    CoreActivity<QuranSurahDetailViewModel>(QuranSurahDetailViewModel::class) {

    private var mediaPlayer: MediaPlayer? = null

    private var isSuccessPrepareAudioPLayer = false

    private val adapter by lazy {
        rvQuranSurah?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_quran_surah_detail
    }

    override fun render() = launch(Dispatchers.Main) {
        viewModel.processIntent(intent)
        setupMediaPlayer()
        setupAppBarListener()
        renderViewTopRound()
        registerObserver()
        setupRecyclerView()
        viewModel.getQuranSurahDetail()
    }

    private fun setupMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer?.setDataSource(viewModel.surah?.audio)
            mediaPlayer?.setOnPreparedListener { it ->
                isSuccessPrepareAudioPLayer = true
                renderAudioPlayer(it.duration)
            }
            mediaPlayer?.setOnCompletionListener {
                viewModel.onClickAudioAction()
            }
            mediaPlayer?.prepare()
        } catch (e: Exception) {
            hideAudioPlayer()
        }
    }

    private fun hideAudioPlayer() {
        audioPlayerCV.gone()
    }

    private fun renderAudioPlayer(audioDuration: Int) {
        audioPlayerCV.visible()
        audioPlayerCV.bind {
            surah = viewModel.surah
            duration = audioDuration
            onClickActionPlayerListener = {
                viewModel.onClickAudioAction()
            }
            onChangeSeekBarListner = { it ->
                setMediaPlayerPosition(it)
            }
        }
    }

    private fun setMediaPlayerPosition(seekbarPosition: Int) = launch {
        try {
            mediaPlayer?.seekTo(seekbarPosition)
        } catch (e: Exception) {
        }
    }

    private fun startMediaPlayer() {
        try {
            mediaPlayer?.start()
            runLooping()
        } catch (e: Exception) {
        }
    }

    private fun runLooping() = launch {
        while (viewModel.isPlayingMurottal) {
            delay(1000L)
            mediaPlayer?.currentPosition?.apply {
                audioPlayerCV.updateSeekbar(this)
            }
        }
    }

    private fun pauseMediaPlayer() {
        try {
            mediaPlayer?.pause()
        } catch (e: Exception) {
        }
    }

    private fun showToolbar(isVisible: Boolean = true) {
        toolbarCV.bind {
            onClickBackListener = {
                onBackPressed()
            }
            toolbarTitle = if (isVisible) {
                viewModel.surah?.namaLatin.orEmpty()
            } else {
                null
            }
        }
    }

    private fun setupAppBarListener() {
        viewModel.surah?.apply {
            tvSurahName.text = namaLatin
            tvSurahMean.text = arti
            tvSurahType.text = showType
            tvTotalAyah.text = "$jumlahAyat ayat"
            tvSurahNameAr.text = nama
        }
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                tvSurahName.alpha = 0F
                tvSurahMean.alpha = 0F
                tvTotalAyah.alpha = 0F
                showToolbar()
            } else {
                tvSurahName.alpha = 1F
                tvSurahMean.alpha = 1F
                tvTotalAyah.alpha = 1F
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
        viewModel.shownQuranSurahDetail.observe(this, Observer {
            it?.apply {
                showBismillah()
                renderList(this)
            }
        })

        viewModel.showLoading.observe(this, Observer { isShow ->
            if (isShow) {
                showLoadingView()
            }
        })

        viewModel.mIsPlayingMurottal.observe(this, Observer { isPlay ->
            audioPlayerCV.renderActionPlayer(isPlay)

            if (isPlay) {
                startMediaPlayer()
            } else {
                pauseMediaPlayer()
            }
        })
    }

    private fun setupRecyclerView() {
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight &&
                    scrollY > oldScrollY
                ) {
                    viewModel.onLoadMore()
                }
            }

            // the delay of the extension of the FAB is set for 12 items
            if (scrollY > oldScrollY + 12 && audioPlayerCV.isVisible) {
                audioPlayerCV.gone()
            }

            // the delay of the extension of the FAB is set for 12 items
            if (scrollY < oldScrollY - 12 && audioPlayerCV.isGone && isSuccessPrepareAudioPLayer) {
                audioPlayerCV.visible()
            }

            // if the nestedScrollView is at the first item of the list then the
            // extended floating action should be in extended state
            if (scrollY == 0 && isSuccessPrepareAudioPLayer) {
                audioPlayerCV.visible()
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
            }.setIdentifier(item.idn)
        }.toMutableList()

        adapter?.diffCalculateAdapter(components)
    }

    private fun showBismillah() {
        tvBismillah.isVisible = viewModel.surah?.isHideBismillah?.not() ?: false
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
                height = 150.dp()
                componentMargin = Rectangle(
                    vertical = Spacing.x4,
                    horizontal = Spacing.x8
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
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