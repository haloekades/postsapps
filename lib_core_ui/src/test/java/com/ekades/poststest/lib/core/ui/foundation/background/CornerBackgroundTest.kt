package com.ekades.poststest.lib.core.ui.foundation.background

import android.os.Build
import com.ekades.poststest.lib.application.ApplicationProvider
import com.ekades.poststest.lib.core.test.BaseAppTest
import com.ekades.poststest.lib.core.test.viewmodels.base.BaseTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import com.ekades.poststest.lib.core.ui.foundation.corner.CornerRadius
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import androidx.test.core.app.ApplicationProvider as TestApp

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class)
class CornerBackgroundTest : BaseTest() {
    
    override fun setup() {
        super.setup()
        mockkObject(ApplicationProvider)
        every { ApplicationProvider.context } returns TestApp.getApplicationContext()
    }
    
    @Test
    fun `small corner background returns correct corner radius`() {
        val background = CornerBackgroundSmall()
        
        background.cornerRadius shouldBe CornerRadius.SMALL.value.toFloat()
    }
    
    @Test
    fun `medium corner background returns correct corner radius`() {
        val background = CornerBackgroundMedium()
        
        background.cornerRadius shouldBe CornerRadius.MEDIUM.value.toFloat()
    }
    
    @Test
    fun `large corner background returns correct corner radius`() {
        val background = CornerBackgroundLarge()
        
        background.cornerRadius shouldBe CornerRadius.LARGE.value.toFloat()
    }
    
    @Test
    fun `XLarge corner background returns correct corner radius`() {
        val background = CornerBackgroundXLarge()
        
        background.cornerRadius shouldBe CornerRadius.XLARGE.value.toFloat()
    }
    
    @Test
    fun `full rounded corner background returns correct corner radius`() {
        val height = 10
        val background = CornerBackgroundFullRounded(height)
        
        background.cornerRadius shouldBe height / 2f
    }
    
    @Test
    fun `large half rounded corner background returns correct corner radius`() {
        val background = CornerBackgroundHalfLargeRounded()
        
        background.cornerRadii?.getOrNull(0) shouldBe CornerRadius.LARGE.value.toFloat()
        background.cornerRadii?.getOrNull(1) shouldBe CornerRadius.LARGE.value.toFloat()
        background.cornerRadii?.getOrNull(2) shouldBe CornerRadius.NONE.value.toFloat()
        background.cornerRadii?.getOrNull(3) shouldBe CornerRadius.NONE.value.toFloat()
        background.cornerRadii?.getOrNull(4) shouldBe CornerRadius.NONE.value.toFloat()
        background.cornerRadii?.getOrNull(5) shouldBe CornerRadius.NONE.value.toFloat()
        background.cornerRadii?.getOrNull(6) shouldBe CornerRadius.LARGE.value.toFloat()
        background.cornerRadii?.getOrNull(7) shouldBe CornerRadius.LARGE.value.toFloat()
    }
    
    @Test
    fun `XLarge half rounded corner background returns correct corner radius`() {
        val background = CornerBackgroundHalfXLargeRounded()
    
        background.cornerRadii?.getOrNull(0) shouldBe CornerRadius.XLARGE.value.toFloat()
        background.cornerRadii?.getOrNull(1) shouldBe CornerRadius.XLARGE.value.toFloat()
        background.cornerRadii?.getOrNull(2) shouldBe CornerRadius.NONE.value.toFloat()
        background.cornerRadii?.getOrNull(3) shouldBe CornerRadius.NONE.value.toFloat()
        background.cornerRadii?.getOrNull(4) shouldBe CornerRadius.NONE.value.toFloat()
        background.cornerRadii?.getOrNull(5) shouldBe CornerRadius.NONE.value.toFloat()
        background.cornerRadii?.getOrNull(6) shouldBe CornerRadius.XLARGE.value.toFloat()
        background.cornerRadii?.getOrNull(7) shouldBe CornerRadius.XLARGE.value.toFloat()
    }
}