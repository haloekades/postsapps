package com.ekades.poststest.lib.ui.asset.attributes

import com.ekades.poststest.lib.ui.asset.R
import com.ekades.poststest.lib.ui.asset.extension.asDimen

enum class IconSize(val identifier: Int, val value: Int) {
    XSMALL(1, R.dimen.image_holder_xsmall.asDimen()),
    SMALL(2, R.dimen.image_holder_small.asDimen()),
    MEDIUM(3, R.dimen.image_holder_medium.asDimen()),
    LARGE(4, R.dimen.image_holder_large.asDimen()),
    XLARGE(5, R.dimen.image_holder_xlarge.asDimen()),
    XXLARGE(6, R.dimen.image_holder_xxlarge.asDimen()),
    XXXLARGE(7, R.dimen.image_holder_xxxlarge.asDimen())
}