package com.ekades.fcmpushnotification.test.ui.photo

import android.content.Intent
import android.os.Build
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekades.fcmpushnotification.lib.core.test.BaseAppTest
import com.ekades.fcmpushnotification.lib.core.test.activities.ActivityScenarioTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.models.Photo
import com.ekades.fcmpushnotification.ui.photo.PhotoDetailActivity
import com.ekades.fcmpushnotification.ui.photo.PhotoDetailActivity.Companion.EXTRA_PHOTO
import com.ekades.fcmpushnotification.ui.photo.PhotoDetailViewModel
import io.mockk.spyk
import kotlinx.android.synthetic.main.activity_photo_detail.*
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class PhotoDetailActivityScenarioTest : ActivityScenarioTest<PhotoDetailActivity>() {
    private lateinit var mockViewModel: PhotoDetailViewModel
    private lateinit var mockPhoto: Photo

    override val koinModules: List<Module>
        get() = listOf(
            module(override = true) {
                viewModel { mockViewModel }
            }
        )

    override fun setup() {
        super.setup()
        mockPhoto = spyk(
            Photo(
                albumId = fake.anInt(),
                id = fake.anInt(),
                title = fake.aString(),
                url = fake.aUrl(),
                thumbnailUrl = fake.aUrl()
            )
        )
        mockViewModel = spyk()
        scenario = launchActivity(
            Intent(applicationContext, PhotoDetailActivity::class.java).apply {
                putExtra(EXTRA_PHOTO, mockPhoto)
            }
        )
    }

    @Test
    fun `validate view is displayed correctly`() {
        scenario.onActivity {
            it.photoTitleTextView.text shouldBe mockPhoto.title
        }
    }
}