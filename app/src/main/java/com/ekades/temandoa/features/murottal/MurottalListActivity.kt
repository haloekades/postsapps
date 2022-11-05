package com.ekades.temandoa.features.murottal

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.ekades.temandoa.R
import com.ekades.temandoa.features.murottal.view.MurottalListItemCV
import com.ekades.temandoa.features.quranlist.model.Surah
import com.ekades.temandoa.lib.application.ui.CoreActivity
import com.ekades.temandoa.lib.core.ui.extension.*
import com.ekades.temandoa.lib.core.ui.foundation.background.CornerBackroundTopMedium
import com.ekades.temandoa.lib.core.ui.foundation.color.ColorPalette
import com.ekades.temandoa.lib.core.ui.foundation.component.Component
import com.ekades.temandoa.lib.core.ui.foundation.component.Rectangle
import com.ekades.temandoa.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.temandoa.lib.core.ui.foundation.container.LinearContainer
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing
import com.ekades.temandoa.lib.ui.asset.extension.dp
import com.ekades.temandoa.lib.ui.component.loading.RectangleSkeletonCV
import kotlinx.android.synthetic.main.activity_murottal_list.*
import kotlinx.android.synthetic.main.activity_murottal_list.mainContentView
import kotlinx.android.synthetic.main.activity_murottal_list.nestedScrollView
import kotlinx.android.synthetic.main.activity_murottal_list.rvQuranSurah
import kotlinx.android.synthetic.main.activity_murottal_list.toolbarCV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MurottalListActivity :
    CoreActivity<MurottalListViewModel>(MurottalListViewModel::class) {

    private var mediaPlayer: MediaPlayer? = null

    private val adapter by lazy {
        rvQuranSurah?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_murottal_list
    }

    override fun render() = launch(Dispatchers.Main) {
        showToolbar()
        renderBgContentView()
        registerObserver()
        setupRecyclerView()
        viewModel.getAllQuranSurah()
    }

    private fun showToolbar() {
        toolbarCV.bind {
            onClickBackListener = {
                onBackPressed()
            }
            toolbarTitle = null
        }
    }

    private fun renderBgContentView() {
        mainContentView.background = CornerBackroundTopMedium().apply {
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

        viewModel.selectedSurah.observe(this, Observer {
            setupMediaPlayer(it)
        })

        viewModel.mIsPlayingMurottal.observe(this, Observer { isPlay ->
            murottalPlayerCV.renderActionPlayer(isPlay)

            if (isPlay) {
                startMediaPlayer()
            } else {
                pauseMediaPlayer()
            }
        })
    }

    private fun bindMurottalPlayerCV(surah: Surah, duration: Int) {
        murottalPlayerCV.visible()
        murottalPlayerCV.bind {
            this.surah = surah
            this.duration = duration
            this.onClickActionPlayerListener = {
                viewModel.setPlayingSurah()
            }
            this.onChangeSeekBarListner = {
                setMediaPlayerPosition(it)
            }
            this.isFirst = viewModel.isFirst
            this.onClickPreviousListener = {
                viewModel.onClickPrevious()
            }
            this.isLast = viewModel.isLast
            this.onClickNextListener = {
                viewModel.onClickNext()
            }
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
                murottalPlayerCV.updateSeekbar(this)
            }
        }
    }

    private fun pauseMediaPlayer() {
        try {
            mediaPlayer?.pause()
        } catch (e: Exception) {
        }
    }

    private fun setMediaPlayerPosition(seekbarPosition: Int) = launch {
        try {
            mediaPlayer?.seekTo(seekbarPosition)
        } catch (e: Exception) {
        }
    }

    private fun setupMediaPlayer(surah: Surah) {
        try {
            resetMediaPlayer()

            mediaPlayer = MediaPlayer()
            mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer?.setDataSource(surah.audio)
            mediaPlayer?.setOnPreparedListener { it ->
                doPlayMurottal(surah, it.duration)
            }
            mediaPlayer?.setOnCompletionListener {
                viewModel.setPlayingSurah()
            }
            mediaPlayer?.prepare()
        } catch (e: Exception) {
            murottalPlayerCV.gone()
        }
    }

    private fun resetMediaPlayer() {
        if (mediaPlayer?.isPlaying != true) {
            viewModel.setPlayingSurah()
        }

        mediaPlayer?.seekTo(0)
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
    }

    private fun doPlayMurottal(surah: Surah, duration: Int) = launch {
        try {
            bindMurottalPlayerCV(surah, duration)
            viewModel.doPlayMurottal()
        } catch (e: Exception) {
        }
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

    private fun renderList(surahSections: List<Surah>) {
        val components: MutableList<Component<*>> = surahSections.mapIndexed { index, item ->
            ConstraintContainer.newComponent({
                MurottalListItemCV(this)
            }) {
                surah = item
                onItemClickListener = { surah ->
                    viewModel.doPlayMurottal(false)
                    viewModel.setSelectedSurah(surah, index)
                }
                onClickPlayListener = {
                    viewModel.setPlayingSurah()
                }
            }.setIdentifier(item.namaLatin + item.isSelected + item.isPlaying)
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
                height = 100.dp()
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
        @JvmStatic
        fun newIntent(context: Context?): Intent {
            return Intent(context, MurottalListActivity::class.java)
        }
    }
}