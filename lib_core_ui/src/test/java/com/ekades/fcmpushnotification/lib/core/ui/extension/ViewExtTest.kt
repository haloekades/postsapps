package com.ekades.fcmpushnotification.lib.core.ui.extension

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekades.fcmpushnotification.lib.application.ApplicationProvider
import com.ekades.fcmpushnotification.lib.core.test.BaseAppTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.lib.core.ui.foundation.spacing.Spacing
import com.ekades.fcmpushnotification.lib.ui.asset.extension.dp
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class ViewExtTest : BaseTest() {
    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity

    private lateinit var view: View

    override fun setup() {
        super.setup()
        mockkStatic("com.ekades.poststest.lib.core.ui.extension.ViewExtKt")

        mockkObject(ApplicationProvider)
        every { ApplicationProvider.context } returns androidx.test.core.app.ApplicationProvider.getApplicationContext()

        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()

        view = View(activity)
    }

    @Test
    fun `set view visible correct`() {
        // When
        view.visible()

        // Then
        view.visibility shouldBe View.VISIBLE
    }

    @Test
    fun `set view gone correct`() {
        // When
        view.gone()

        // Then
        view.visibility shouldBe View.GONE
    }

    @Test
    fun `set view invisible correct`() {
        // When
        view.invisible()

        // Then
        view.visibility shouldBe View.INVISIBLE
    }

    @Test
    fun `set view enable correct`() {
        // When
        view.enable()

        // Then
        view.isEnabled shouldBe true
    }

    @Test
    fun `set view disable correct`() {
        // When
        view.disable()

        // Then
        view.isEnabled shouldBe false
    }

    @Test
    fun `set show view with condition correct`() {
        // Given
        val x = 1
        view.gone()


        // When
        view.showIf { x == 1 }

        // Then
        view.visibility shouldBe View.VISIBLE
    }

    @Test
    fun `set show view with condition not correct`() {
        // Given
        val x = 1
        view.gone()


        // When
        view.showIf { x == 2 }

        // Then
        view.visibility shouldBe View.GONE
    }

    @Test
    fun `set hide view with condition correct`() {
        // Given
        val x = 1
        view.visible()


        // When
        view.hideIf { x == 1 }

        // Then
        view.visibility shouldBe View.INVISIBLE
    }

    @Test
    fun `set hide view with condition not correct`() {
        // Given
        val x = 1
        view.visible()


        // When
        view.hideIf { x == 2 }

        // Then
        view.visibility shouldBe View.VISIBLE
    }

    @Test
    fun `set remove view with condition correct`() {
        // Given
        val x = 1
        view.visible()


        // When
        view.removeIf { x == 1 }

        // Then
        view.visibility shouldBe View.GONE
    }

    @Test
    fun `set remove view with condition not correct`() {
        // Given
        val x = 1
        view.visible()


        // When
        view.removeIf { x == 2 }

        // Then
        view.visibility shouldBe View.VISIBLE
    }

    @Test
    fun `set view padding with all param`() {
        // When
        view.setViewPadding(1.dp(), 1.dp(), 1.dp(), 1.dp())

        // Then
        view.run {
            paddingLeft shouldBe 1.dp()
            paddingRight shouldBe 1.dp()
            paddingTop shouldBe 1.dp()
            paddingBottom shouldBe 1.dp()
        }
    }

    @Test
    fun `set view padding with all param as Spacing`() {
        // When
        view.setViewPadding(Spacing.x16)

        // Then
        view.run {
            paddingLeft shouldBe Spacing.x16.value
            paddingRight shouldBe Spacing.x16.value
            paddingTop shouldBe Spacing.x16.value
            paddingBottom shouldBe Spacing.x16.value
        }
    }

    @Test
    fun `set view padding with horizontal & vertical param as Spacing`() {
        // When
        view.setViewPadding(horizontal = Spacing.x12, vertical = Spacing.x16)

        // Then
        view.run {
            paddingLeft shouldBe Spacing.x12.value
            paddingRight shouldBe Spacing.x12.value
            paddingTop shouldBe Spacing.x16.value
            paddingBottom shouldBe Spacing.x16.value
        }
    }

    @Test
    fun `set view padding with selected sides param as Spacing`() {
        // When
        view.setViewPadding(left = Spacing.x12, top = Spacing.x16)

        // Then
        view.run {
            paddingLeft shouldBe Spacing.x12.value
            paddingRight shouldBe paddingRight
            paddingTop shouldBe Spacing.x16.value
            paddingBottom shouldBe paddingBottom
        }
    }

    @Test
    fun `set view margin with all param`() {
        // When
        view.layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        view.setViewMargin(1.dp(), 1.dp(), 1.dp(), 1.dp())

        // Then
        view.run {
            marginLeft shouldBe 1.dp()
            marginRight shouldBe 1.dp()
            marginTop shouldBe 1.dp()
            marginBottom shouldBe 1.dp()
        }
    }

    @Test
    fun `set view margin with all param as Spacing`() {
        // When
        view.layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        view.setViewMargin(Spacing.x16)

        // Then
        view.run {
            marginLeft shouldBe Spacing.x16.value
            marginRight shouldBe Spacing.x16.value
            marginTop shouldBe Spacing.x16.value
            marginBottom shouldBe Spacing.x16.value
        }
    }

    @Test
    fun `set view margin with horizontal & vertical param as Spacing`() {
        // When
        view.layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        view.setViewMargin(horizontal = Spacing.x12, vertical = Spacing.x16)

        // Then
        view.run {
            marginLeft shouldBe Spacing.x12.value
            marginRight shouldBe Spacing.x12.value
            marginTop shouldBe Spacing.x16.value
            marginBottom shouldBe Spacing.x16.value
        }
    }

    @Test
    fun `set view margin with selected sides param as Spacing`() {
        // When
        view.layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        view.setViewMargin(left = Spacing.x12, top = Spacing.x16)

        // Then
        view.run {
            marginLeft shouldBe Spacing.x12.value
            marginRight shouldBe marginRight
            marginTop shouldBe Spacing.x16.value
            marginBottom shouldBe marginBottom
        }
    }
}