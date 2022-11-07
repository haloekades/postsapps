package com.ekades.temandoa.features.audioplayer.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar
import com.ekades.temandoa.R
import com.ekades.temandoa.features.quranlist.model.Surah
import com.ekades.temandoa.lib.core.ui.extension.gone
import com.ekades.temandoa.lib.core.ui.extension.visible
import com.ekades.temandoa.lib.core.ui.foundation.background.CornerBackgroundFullRounded
import com.ekades.temandoa.lib.core.ui.foundation.color.ColorPalette
import com.ekades.temandoa.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing
import com.ekades.temandoa.lib.ui.asset.attributes.IconSize
import com.ekades.temandoa.lib.ui.asset.extension.dp
import kotlinx.android.synthetic.main.cv_murottal_player.view.*
import java.util.concurrent.TimeUnit

class MurottalPlayerCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<MurottalPlayerCV.State>(context, attributeSet, defStyle) {

    private var isAutoChangeSeekbar = false

    class State {
        var surah: Surah? = null
        var duration: Int? = null
        var onClickActionPlayerListener: (() -> Unit)? = null
        var onChangeSeekBarListner: ((Int) -> Unit)? = null
        var isFirst: Boolean = false
        var isLast: Boolean = false
        var onClickPreviousListener: (() -> Unit)? = null
        var onClickNextListener: (() -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_murottal_player, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            surah?.apply {
                bgSurahAr.background = CornerBackgroundFullRounded(Spacing.x64.value).apply {
                    setStroke(3.dp(), ColorPalette.WHITE)
                }
                tvSurahNameAr.text = nama
                tvSurahName.text = namaLatin
                tvSurahMean.text = arti
                tvMaxDuration.text = duration?.convertToShownTime()
                bgActionPlayer.background = CornerBackgroundFullRounded(Spacing.x48.value).apply {
                    setStroke(3.dp(), ColorPalette.WHITE)
                }

                icActionPlayer.setOnClickListener {
                    onClickActionPlayerListener?.invoke()
                }
                renderActionPlayer()
                renderPreviousPlayer(isFirst, onClickPreviousListener)
                renderNextPlayer(isLast, onClickNextListener)
                seekBar.max = duration ?: 0
                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

    private fun renderPreviousPlayer(isFirst: Boolean, onClickPreviousListener: (() -> Unit)?) {
        if (isFirst) {
            icActionPrevious.gone()
            bgActionPrevious.gone()
        } else {
            icActionPrevious.visible()
            bgActionPrevious.visible()

            icActionPrevious.bind {
                imageDrawable = R.drawable.ic_previous
                imageSize = IconSize.XSMALL
                imageTint = ColorPalette.WHITE
            }
            bgActionPrevious.background = CornerBackgroundFullRounded(Spacing.x36.value).apply {
                setStroke(3.dp(), ColorPalette.WHITE)
            }
            bgActionPrevious.setOnClickListener {
                onClickPreviousListener?.invoke()
            }
        }
    }

    private fun renderNextPlayer(isLast: Boolean, onClickNextListener: (() -> Unit)?) {
        if (isLast) {
            icActionNext.gone()
            bgActionNext.gone()
        } else {
            icActionNext.visible()
            bgActionNext.visible()

            icActionNext.bind {
                imageDrawable = R.drawable.ic_previous
                imageSize = IconSize.XSMALL
                imageTint = ColorPalette.WHITE
            }
            icActionNext.rotation = 180f

            bgActionNext.background = CornerBackgroundFullRounded(Spacing.x36.value).apply {
                setStroke(3.dp(), ColorPalette.WHITE)
            }
            bgActionNext.setOnClickListener {
                onClickNextListener?.invoke()
            }
        }
    }

    fun updateSeekbar(currentDuration: Int) {
        isAutoChangeSeekbar = true
        seekBar.progress = currentDuration
        updateShownDuration(currentDuration)
    }

    private fun updateShownDuration(currentDuration: Int) {
        tvCurrentDuration.text = currentDuration.convertToShownTime()
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