package com.ekades.fcmpushnotification.test.ui.photo

import android.content.Intent
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseViewModelTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.models.Photo
import com.ekades.fcmpushnotification.ui.photo.PhotoDetailActivity
import com.ekades.fcmpushnotification.ui.photo.PhotoDetailViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Test

class PhotoDetailViewModelTest : BaseViewModelTest<PhotoDetailViewModel>() {

    override fun setup() {
        super.setup()
        mViewModel = spyk()
    }

    @Test
    fun `verify process intent is correctly`() {
        // Inject
        val mockPhoto = mockk<Photo>()
        val mockIntent: Intent = mockk(relaxed = true)

        // Given
        every { mockIntent.hasExtra(PhotoDetailActivity.EXTRA_PHOTO) } returns true
        every { mockIntent.getParcelableExtra<Photo>(PhotoDetailActivity.EXTRA_PHOTO) } returns mockPhoto

        // When
        mViewModel.processIntent(mockIntent)

        // Then
        mViewModel.photo shouldBe mockPhoto
    }
}