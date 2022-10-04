package com.ekades.temandoa.lib.ui.component.base

import com.ekades.temandoa.lib.core.ui.foundation.component.Rectangle

open class ComponentState {
    /**
     * Margin for Component as [Rectangle]
     */
    var componentMargin: Rectangle? = null

    /**
     * Padding for Component as [Rectangle]
     */
    var componentPadding: Rectangle? = null

    /**
     * Whether component is enabled or disabled
     */
    var isEnabled: Boolean = true
}