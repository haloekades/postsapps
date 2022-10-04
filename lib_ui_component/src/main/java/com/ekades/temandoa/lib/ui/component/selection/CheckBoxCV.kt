package com.ekades.temandoa.lib.ui.component.selection

import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.ColorInt
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.CompoundButtonCompat
import com.airbnb.paris.extensions.style
import com.ekades.temandoa.lib.core.ui.extension.gone
import com.ekades.temandoa.lib.core.ui.extension.setViewMargin
import com.ekades.temandoa.lib.core.ui.extension.setViewPadding
import com.ekades.temandoa.lib.core.ui.foundation.color.ColorPalette
import com.ekades.temandoa.lib.core.ui.foundation.component.setComponentMargin
import com.ekades.temandoa.lib.core.ui.foundation.component.setComponentPadding
import com.ekades.temandoa.lib.core.ui.foundation.container.LinearContainer
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing
import com.ekades.temandoa.lib.ui.component.R
import com.ekades.temandoa.lib.ui.component.base.ComponentState
import com.ekades.temandoa.lib.ui.component.extension.ComponentUtils
import com.ekades.temandoa.lib.ui.component.extension.ComponentUtils.colorStateList
import com.ekades.temandoa.lib.ui.component.extension.maxLength

class CheckBoxCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearContainer<CheckBoxCV.State>(context, attributeSet, defStyle) {
    @VisibleForTesting
    val checkBox = AppCompatCheckBox(context)
    @VisibleForTesting
    val description = AppCompatTextView(context)

    init {
        orientation = HORIZONTAL

        checkBox.apply {
            layoutParams = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            style(R.style.Body2)
            scaleX = SCALE_SIZE
            scaleY = SCALE_SIZE
            minimumHeight = 0
            minimumWidth = 0
        }

        description.apply {
            layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            maxLength(MAX_DESCRIPTION_LENGTH)
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(ColorPalette.TUNDORA)
            setViewMargin(left = Spacing.x8.value)
            setViewPadding(bottom = Spacing.x8)
        }

        addView(checkBox)
        addView(description)
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) state@{
            setComponentPadding(componentPadding)
            setComponentMargin(componentMargin)

            checkBox.apply {
                CompoundButtonCompat.setButtonTintList(this, renderCheckBoxTheme())
                setTextColor(ColorPalette.WHITE)
                setOnCheckedChangeListener { _, isChecked ->
                    onCheckChangedListener?.invoke(isChecked)
                }
                isChecked = this@state.isChecked
                isEnabled = this@state.isEnabled
            }

            description.apply {
                setDescriptionStyle(checkBoxDescriptionType, state.isEnabled)
                text = checkBoxDescription
            }
        }
    }

    private fun renderCheckBoxTheme(): ColorStateList {
        return colorStateList(
                ComponentUtils.checkedEnabled to Theme.CHECKED_ENABLED.color,
                ComponentUtils.checkedDisabled to Theme.CHECKED_DISABLED.color,
                ComponentUtils.uncheckedEnabled to Theme.UNCHECKED_ENABLED.color,
                ComponentUtils.uncheckedDisabled to Theme.UNCHECKED_DISABLED.color
        )
    }

    private fun setDescriptionStyle(type: Type, isViewEnable: Boolean) {
        when (type) {
            Type.DEFAULT -> {
                description.style(R.style.Body2)
                setContainerPadding(horizontal = Spacing.x0, vertical = Spacing.x16)
            }
            Type.TNC -> {
                description.style(R.style.Body4)
                setContainerPadding(horizontal = Spacing.x16, vertical = Spacing.x12)
            }
            Type.HIDDEN -> {
                description.gone()
                setContainerPadding(horizontal = Spacing.x0, vertical = Spacing.x0)
            }
        }

        val descriptionColor = if (isViewEnable) {
            ColorPalette.TUNDORA
        } else {
            ColorPalette.MERCURY
        }
        description.setTextColor(descriptionColor)
    }

    enum class Theme(@ColorInt val color: Int) {
        CHECKED_ENABLED(ColorPalette.BRAND),
        CHECKED_DISABLED(ColorPalette.FRINGY_FLOWER),
        UNCHECKED_ENABLED(ColorPalette.ALTO),
        UNCHECKED_DISABLED(ColorPalette.MERCURY)
    }

    enum class Type {
        DEFAULT,
        TNC,
        HIDDEN
    }

    class State : ComponentState() {
        /**
         * CheckBox default check status on bind, default is false
         * @see [AppCompatCheckBox.isChecked]
         */
        var isChecked: Boolean = false

        /**
         * CheckBox description as [CharSequence], can accept Span
         */
        var checkBoxDescription: CharSequence? = null

        /**
         * CheckBox type as [Type], default is [Type.DEFAULT]
         */
        var checkBoxDescriptionType: Type = Type.DEFAULT

        /**
         * CheckBox on Check Change Listener, returns [AppCompatCheckBox.isChecked] in Lambda
         */
        var onCheckChangedListener: ((Boolean) -> Unit)? = null
    }

    companion object {
        const val SCALE_SIZE = 1.2f
        const val MAX_DESCRIPTION_LENGTH = 180
    }
}