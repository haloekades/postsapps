package com.ekades.poststest.test.components

import android.app.Activity
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekades.poststest.components.ThumbnailPhotoCV
import com.ekades.poststest.lib.application.ApplicationProvider
import com.ekades.poststest.lib.core.test.BaseAppTest
import com.ekades.poststest.lib.core.test.viewmodels.base.BaseTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import com.ekades.poststest.models.Photo
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.spyk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import androidx.test.core.app.ApplicationProvider as TestApp

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class ThumbnailPhotoCVTest : BaseTest() {

    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity
    private lateinit var thumbnailPhotoCV: ThumbnailPhotoCV

    override fun setup() {
        super.setup()
        mockkObject(ApplicationProvider)
        every { ApplicationProvider.context } returns TestApp.getApplicationContext()

        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()
        thumbnailPhotoCV = ThumbnailPhotoCV(activity)
    }

    override fun tearAndDown() {
        super.tearAndDown()
        activityController.get().finish()
    }

    @Test
    fun `assert default state should return correct value`() {
        val mockPhoto = spyk(
            Photo(
                albumId = fake.anInt(),
                id = fake.anInt(),
                title = fake.aString(),
                url = fake.aUrl(),
                thumbnailUrl = fake.aUrl()
            )
        )

        thumbnailPhotoCV.bind {
            photo = mockPhoto
            onClickListener = { photo ->
                photo shouldBe mockPhoto
            }
        }

        thumbnailPhotoCV.performClick()
    }
}