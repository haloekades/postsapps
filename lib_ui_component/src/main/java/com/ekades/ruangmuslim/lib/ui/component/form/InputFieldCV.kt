package com.ekades.ruangmuslim.lib.ui.component.form

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.ekades.ruangmuslim.lib.core.ui.extension.gone
import com.ekades.ruangmuslim.lib.core.ui.extension.setTypography
import com.ekades.ruangmuslim.lib.core.ui.extension.setViewPadding
import com.ekades.ruangmuslim.lib.core.ui.extension.visible
import com.ekades.ruangmuslim.lib.core.ui.foundation.background.CornerBackgroundMedium
import com.ekades.ruangmuslim.lib.core.ui.foundation.color.ColorPalette
import com.ekades.ruangmuslim.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.ruangmuslim.lib.core.ui.foundation.spacing.Spacing
import com.ekades.ruangmuslim.lib.ui.asset.extension.dp
import com.ekades.ruangmuslim.lib.ui.component.R
import com.ekades.ruangmuslim.lib.ui.component.extension.ComponentUtils
import kotlinx.android.synthetic.main.cv_input_field.view.*

class InputFieldCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<InputFieldCV.State>(context, attributeSet, defStyle) {

    private var inputFilters: MutableList<InputFilter> = mutableListOf()

    init {
        View.inflate(context, R.layout.cv_input_field, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        setupInputFilters(state)
        renderInputTitle(state.inputTitle)
        renderInputDescription(state.inputDescription)
        renderInputText(state)
        renderInputTextColor()
        setupInputTextListener(state)
        renderFooter(state.inputFooter, state.inputErrorText)
    }

    private fun setupInputFilters(state: State) {
        if (state.inputMaxLength > 0) {
            inputFilters.add(InputFilter.LengthFilter(state.inputMaxLength))
        }
    }

    private fun renderInputTitle(inputTitle: String?) {
        inputTitleTextView.run {
            if (inputTitle.isNullOrBlank()) gone() else visible()
            text = inputTitle
            isVisible = inputTitle.isNullOrBlank().not()
        }
    }

    private fun renderInputDescription(inputDescription: String?) {
        inputDescriptionTextView.run {
            if (inputDescription.isNullOrBlank()) gone() else visible()
            text = inputDescription
            isVisible = inputDescription.isNullOrBlank().not()
        }
    }

    @SuppressLint("WrongConstant")
    private fun renderInputText(state: State) {
        state.inputLeftIcon?.let {
            inputIcon.visible()
            inputIcon.bind {
                imageDrawable = it
            }
        } ?: run {
            inputIcon.gone()
        }

        state.inputRightIcon?.let {
            inputRightIcon.isVisible = state.isAlwaysShowRightIcon ?: false
            inputRightIcon.setImageDrawable(ContextCompat.getDrawable(context, it))
            inputRightIcon.setOnClickListener {
                state.onRightIconClick?.invoke()
            }
        } ?: run {
            inputRightIcon.gone()
        }

        inputEditText.run {
            hint = state.inputHint
            isEnabled = state.isEnabled
            inputType = state.inputType
            filters = inputFilters.toTypedArray()
            setText(state.inputText)
            setTypography(state.inputSize.typography)

            val paddingLeft = when (state.inputSize) {
                InputSize.SMALL -> Spacing.x32.value
                InputSize.MEDIUM -> Spacing.x42.value
                InputSize.LARGE -> Spacing.x42.value
            }

            val paddingRight = when (state.inputSize) {
                InputSize.SMALL -> Spacing.x32.value
                InputSize.MEDIUM -> Spacing.x42.value
                InputSize.LARGE -> Spacing.x42.value
            }
            setViewPadding(
                left = if (state.inputLeftIcon != null) {
                    paddingLeft
                } else {
                    state.inputSize.padding.value
                },
                right = if (state.inputRightIcon != null) {
                    paddingRight
                } else {
                    state.inputSize.padding.value
                },
                top = state.inputSize.padding.value,
                bottom = state.inputSize.padding.value
            )

            state.imeOptions?.apply { imeOptions = this }
            setOnEditorActionListener { text, actionId, _ ->
                state.onEditorActionListener?.let { it(actionId, text.toString()) }
                    ?: run {
                        val next = text.focusSearch(FOCUS_FORWARD)
                        next?.requestFocus()
                    }
                true
            }
            if (state.onEditTextClickListener != null) {
                isFocusable = false
                setOnClickListener {
                    state.onEditTextClickListener?.invoke()
                }
            }
        }

        inputIcon.layoutParams.run {
            width = state.inputSize.iconSize.value
            height = state.inputSize.iconSize.value
        }
    }

    private fun setupInputTextListener(state: State) {
        inputEditText.doAfterTextChanged { text ->
            state.onErrorValidationText?.let { onErrorValidationText ->
                renderFooter(state.inputFooter, onErrorValidationText.invoke(text?.toString()
                    .orEmpty()))
            }

            if (state.isAlwaysShowRightIcon == false) {
                inputRightIcon.isVisible = (state.inputRightIcon != null && text?.isNotEmpty() == true)
            }
            state.onAfterTextChangedListener?.invoke(text?.toString().orEmpty())
        }
    }

    private fun renderFooter(defaultText: String?, inputErrorText: String?) {
        if (inputLabelTextView.text.toString().equals(inputErrorText, false)) {
            return
        }

        if (inputErrorText.isNullOrBlank().not() && inputEditText.isEnabled) {
            inputLabelTextView.run {
                text = inputErrorText
                setTextColor(ColorPalette.ROSE_MADDER)
                visible()
            }

            inputEditText.background = CornerBackgroundMedium().apply {
                setColor(ColorPalette.WHITE)
                setStroke(1.dp(), ColorPalette.ROSE_MADDER)
            }
        } else {
            inputLabelTextView.run {
                text = defaultText
                if (defaultText.isNullOrBlank()) {
                    gone()
                } else {
                    setTextColor(ColorPalette.WHITE)
                    visible()
                }
            }
            renderInputTextBackground()
        }
    }

    private fun renderInputTextBackground() {
        val defaultDrawable = CornerBackgroundMedium().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1.dp(), ColorPalette.ALTO)
        }

        val focusedDrawable = CornerBackgroundMedium().apply {
            setColor(ColorPalette.WHITE)
            setStroke(1.dp(), ColorPalette.BRAND)
        }

        val disabledDrawable = CornerBackgroundMedium().apply {
            setColor(ColorPalette.WILD_SAND)
            setStroke(1.dp(), ColorPalette.ALTO)
        }

        val stateListDrawable = ComponentUtils.drawableStateList(
            ComponentUtils.focusedEnabled to focusedDrawable,
            ComponentUtils.disabled to disabledDrawable,
            ComponentUtils.default to defaultDrawable
        )
        inputEditText.background = stateListDrawable
    }

    private fun renderInputTextColor() {
        val inputTextColors = ComponentUtils.colorStateList(
            ComponentUtils.disabled to ColorPalette.DUSTY_GRAY,
            ComponentUtils.default to ColorPalette.TUNDORA
        )
        inputEditText.setTextColor(inputTextColors)
    }

    fun bindErrorText(errorText: String?) {
        renderFooter(null, errorText)
    }

    fun getInputText(): String {
        return inputEditText.text.toString()
    }

    class State : BaseInputState() {
        /**
         * Input type as [InputType]
         */
        var inputType: Int = InputType.TYPE_CLASS_TEXT

        /**
         * Input Icon as [DrawableRes]
         */
        @DrawableRes
        var inputLeftIcon: Int? = null

        /**
         * Input Icon as [DrawableRes]
         */
        @DrawableRes
        var inputRightIcon: Int? = null

        /**
         * Input max length as [Int]
         */
        var inputMaxLength: Int = 0

        var imeOptions: Int? = null
        var onEditorActionListener: ((Int, String) -> Unit)? = null
        var onRightIconClick: (() -> Unit)? = null
        var isAlwaysShowRightIcon: Boolean? = false
        var onEditTextClickListener: (() -> Unit)? = null
    }
}