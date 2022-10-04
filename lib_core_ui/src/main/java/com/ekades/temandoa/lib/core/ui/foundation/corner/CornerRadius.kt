package com.ekades.temandoa.lib.core.ui.foundation.corner

import com.ekades.temandoa.lib.core.ui.R
import com.ekades.temandoa.lib.ui.asset.extension.asDimen

enum class CornerRadius(val value: Int) {
    NONE(0),
    SMALL(R.dimen.corner_small.asDimen()),
    MEDIUM(R.dimen.corner_medium.asDimen()),
    LARGE(R.dimen.corner_large.asDimen()),
    XLARGE(R.dimen.corner_xlarge.asDimen()),
    XXLARGE(R.dimen.corner_xxlarge.asDimen())
}