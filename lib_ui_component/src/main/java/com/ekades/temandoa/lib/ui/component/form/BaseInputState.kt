package com.ekades.temandoa.lib.ui.component.form

import androidx.annotation.StyleRes
import com.ekades.temandoa.lib.core.ui.foundation.spacing.Spacing
import com.ekades.temandoa.lib.ui.asset.attributes.IconSize
import com.ekades.temandoa.lib.ui.component.base.ComponentState

abstract class BaseInputState : ComponentState() {
    /**
     * Input title as [String]
     */
    var inputTitle: String? = null

    /**
     * Input description as [String]
     */
    var inputDescription: String? = null

    /**
     * Input footer as [String]
     */
    var inputFooter: String? = null

    /**
     * Input hint as [String]
     */
    var inputHint: String? = null

    /**
     * Input text as [String]
     */
    var inputText: String? = null

    /**
     * Input error text as [String]
     */
    var inputErrorText: String? = null

    /**
     * Input size as [InputSize]
     */
    var inputSize: InputSize = InputSize.LARGE

    /**
     * After text changed listener, callback with parameter text as [String]
     */
    var onAfterTextChangedListener: ((text: String) -> Unit)? = null

    /**
     * Error validation text, callback with parameter text as [String], and return [String]
     */
    var onErrorValidationText: ((text: String) -> String?)? = null
}

enum class InputSize(
    @StyleRes
    val typography: Int,
    val iconSize: IconSize,
    val padding: Spacing
) {
    SMALL(com.ekades.temandoa.lib.core.ui.foundation.typography.Typography.CAPTION_2, IconSize.SMALL, Spacing.x8),
    MEDIUM(com.ekades.temandoa.lib.core.ui.foundation.typography.Typography.BODY_4, IconSize.MEDIUM, Spacing.x8),
    LARGE(com.ekades.temandoa.lib.core.ui.foundation.typography.Typography.BODY_2, IconSize.MEDIUM, Spacing.x12)
}