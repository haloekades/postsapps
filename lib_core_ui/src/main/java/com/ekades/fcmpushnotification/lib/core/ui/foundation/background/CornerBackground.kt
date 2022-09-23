package com.ekades.fcmpushnotification.lib.core.ui.foundation.background

import com.ekades.fcmpushnotification.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.fcmpushnotification.lib.core.ui.foundation.corner.CornerRadius.LARGE
import com.ekades.fcmpushnotification.lib.core.ui.foundation.corner.CornerRadius.NONE
import com.ekades.fcmpushnotification.lib.core.ui.foundation.corner.CornerRadius.SMALL
import com.ekades.fcmpushnotification.lib.core.ui.foundation.corner.CornerRadius.MEDIUM
import com.ekades.fcmpushnotification.lib.core.ui.foundation.corner.CornerRadius.XLARGE

/**
 * Returns [CornerBackgroundSmall] with 2dp Corners
 */
class CornerBackgroundSmall : BaseCornerBackground(SMALL)

/**
 * Returns [CornerBackgroundMedium] with 4dp Corners
 */
class CornerBackgroundMedium : BaseCornerBackground(MEDIUM)

/**
 * Returns [CornerBackgroundLarge] with 8dp Corners
 */
class CornerBackgroundLarge : BaseCornerBackground(LARGE)

/**
 * Returns [CornerBackgroundXLarge] with 16dp Corners
 */
class CornerBackgroundXLarge : BaseCornerBackground(XLARGE)

/**
 * Returns [CornerBackgroundFullRounded] with full rounded [CornerRadius]
 * @param height The height of your view in px as [Int]
 */
class CornerBackgroundFullRounded(height: Int) : BaseCornerBackground(height / 2)

/**
 * Returns [CornerBackgroundHalfLargeRounded] with dynamic rounded corner [CornerRadius]
 */
class CornerBackgroundHalfLargeRounded : DynamicCornerBackground(LARGE, NONE, NONE, LARGE)

/**
 * Returns [CornerBackgroundHalfXLargeRounded] with dynamic rounded corner [CornerRadius]
 */
class CornerBackgroundHalfXLargeRounded : DynamicCornerBackground(XLARGE, NONE, NONE, XLARGE)