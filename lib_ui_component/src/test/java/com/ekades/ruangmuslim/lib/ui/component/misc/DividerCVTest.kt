package com.ekades.ruangmuslim.lib.ui.component.misc

import android.app.Activity
import android.os.Build
import com.ekades.ruangmuslim.lib.application.ApplicationProvider
import com.ekades.ruangmuslim.lib.core.test.BaseAppTest
import com.ekades.ruangmuslim.lib.core.test.viewmodels.base.BaseTest
import com.ekades.ruangmuslim.lib.core.test.viewmodels.base.shouldBe
import com.ekades.ruangmuslim.lib.core.test.viewmodels.base.shouldNotBe
import io.mockk.every
import io.mockk.mockkObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import androidx.test.core.app.ApplicationProvider as TestApp

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class DividerCVTest : BaseTest() {
    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity

    private lateinit var dividerCV: DividerCV

    @Before
    fun setUp() {
        mockkObject(ApplicationProvider)
        every { ApplicationProvider.context } returns TestApp.getApplicationContext()

        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()

        dividerCV = DividerCV(activity)
    }

    @After
    fun tearDown() {
        activityController.get().finish()
    }

    @Test
    fun `divider should not be null`() {
        dividerCV.divider shouldNotBe null
    }

    @Test
    fun `state default value returns correct value`() {
        dividerCV.bind {
            dividerStyle shouldBe DividerCV.DividerStyle.STRAIGHT
        }
    }
}