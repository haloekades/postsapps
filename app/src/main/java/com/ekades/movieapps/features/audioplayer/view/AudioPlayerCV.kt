package com.ekades.movieapps.features.audioplayer.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar
import com.ekades.movieapps.R
import com.ekades.movieapps.features.quranlist.model.Surah
import com.ekades.movieapps.lib.core.ui.foundation.background.CornerBackgroundFullRounded
import com.ekades.movieapps.lib.core.ui.foundation.background.shadowLarge
import com.ekades.movieapps.lib.core.ui.foundation.color.ColorPalette
import com.ekades.movieapps.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.movieapps.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.movieapps.lib.core.ui.foundation.spacing.Spacing
import com.ekades.movieapps.lib.ui.asset.attributes.IconSize
import com.ekades.movieapps.lib.ui.asset.extension.dp
import kotlinx.android.synthetic.main.cv_audio_player.view.*
import java.util.concurrent.TimeUnit

class AudioPlayerCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<AudioPlayerCV.State>(context, attributeSet, defStyle) {

    private var isAutoChangeSeekbar = false

    private var maxDuration = ""

    class State {
        var surah: Surah? = null
        var duration: Int? = null
        var onClickActionPlayerListener: (() -> Unit)? = null
        var onChangeSeekBarListner: ((Int) -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_audio_player, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        shadowLarge(CornerRadius.XLARGE, ColorPalette.BRAND_80)
    }

    override fun initState(): State = State()

    @SuppressLint("SetTextI18n")
    override fun render(state: State) {
        with(state) {
            surah?.apply {
                maxDuration = duration?.convertToShownTime() ?: ""
                tvSurahName.text = namaLatin
                updateDurationView()
                bgActionPlayer.background = CornerBackgroundFullRounded(Spacing.x42.value).apply {
                    setStroke(3.dp(), ColorPalette.WHITE)
                }
                renderActionPlayer()

                icActionPlayer.setOnClickListener {
                    onClickActionPlayerListener?.invoke()
                }
                seekBar.max = duration ?: 0
                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        if (isAutoChangeSeekbar) {
                            isAutoChangeSeekbar = false
                        } else {
                            onChangeSeekBarListner?.invoke(p1)
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                    }
                })
            }
        }
    }

    fun renderActionPlayer(isStartMediaPlayer: Boolean = false) {
        icActionPlayer.bind {
            imageDrawable = if (isStartMediaPlayer) {
                R.drawable.ic_pause
            } else {
                R.drawable.ic_play
            }
            imageSize = IconSize.SMALL
            imageTint = ColorPalette.WHITE
        }
    }

    fun updateSeekbar(currentDuration: Int) {
        isAutoChangeSeekbar = true
        seekBar.progress = currentDuration
        updateDurationView(currentDuration)
    }

    @SuppressLint("SetTextI18n")
    private fun updateDurationView(currentDuration: Int = 0) {
        tvDuration.text = "${currentDuration.convertToShownTime()} / $maxDuration"
    }

    private fun Int.convertToShownTime(): String {
        val getDurationMillis: Int = this

        val convertHours =
            String.format("%02d", TimeUnit.MILLISECONDS.toHours(getDurationMillis.toLong()))
        val convertMinutes = String.format(
            "%02d", TimeUnit.MILLISECONDS.toMinutes(getDurationMillis.toLong()) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(getDurationMillis.toLong()))
        ) //I needed to add this part.

        val convertSeconds = String.format(
            "%02d", TimeUnit.MILLISECONDS.toSeconds(getDurationMillis.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getDurationMillis.toLong()))
        )

        return "$convertHours:$convertMinutes:$convertSeconds"
    }
}