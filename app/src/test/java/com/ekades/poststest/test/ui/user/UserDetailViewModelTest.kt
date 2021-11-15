package com.ekades.poststest.test.ui.user

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ekades.poststest.lib.core.test.viewmodels.base.BaseViewModelTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import com.ekades.poststest.models.*
import com.ekades.poststest.ui.post.PostDetailActivity
import com.ekades.poststest.ui.user.UserDetailViewModel
import io.mockk.*
import org.junit.Rule
import org.junit.Test

class UserDetailViewModelTest : BaseViewModelTest<UserDetailViewModel>() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    override fun setup() {
        super.setup()
        mViewModel = spyk()
    }

    @Test
    fun `verify process intent is correctly`() {
        // Inject
        val mockUser = mockk<User>()
        val mockIntent: Intent = mockk(relaxed = true)

        // Given
        every { mockIntent.hasExtra(PostDetailActivity.EXTRA_USER) } returns true
        every { mockIntent.getParcelableExtra<User>(PostDetailActivity.EXTRA_USER) } returns mockUser

        // When
        mViewModel.processIntent(mockIntent)

        // Then
        mViewModel.user shouldBe mockUser
    }

    @Test
    fun `verify that loading is called while API get Albums response is loading`() {
        // Given
        val response = mockLoadingResponse()

        // When
        mViewModel.handleResponseGetAlbums(response)

        // Then
        verify(exactly = 1) { mViewModel.showLoading() }
    }

    @Test
    fun `verify that error message is set correctly message when API get Albums response is error`() {
        // Inject
        val response = mockErrorResponse()

        // When
        mViewModel.handleResponseGetAlbums(response)

        // Then
        verify { mViewModel.handleResponseGetAlbums(any()) }
        verify(exactly = 1) { mViewModel.showLoading(false) }
    }

    @Test
    fun `verify get Albums is correct when response successful`() {
        // Inject
        val mockResponse = mockk<ArrayList<Album>>()
        val response = mockSuccessResponse()

        // Given
        every { mViewModel.getPhotos() } just runs
        every { mViewModel.convertAlbumsResponse(any()) } returns mockResponse

        // When
        mViewModel.handleResponseGetAlbums(response)

        // Then
        mViewModel.convertAlbumsResponse(response) shouldBe mockResponse
        mViewModel.albums shouldBe mockResponse
        verify { mViewModel.handleResponseGetAlbums(any()) }
    }

    @Test
    fun `verify that loading is called while API get photos response is loading`() {
        // Given
        val response = mockLoadingResponse()

        // When
        mViewModel.handleResponseGetPhotos(response)

        // Then
        verify(exactly = 1) { mViewModel.showLoading() }
    }

    @Test
    fun `verify that error message is set correctly message when API get photos response is error`() {
        // Inject
        val response = mockErrorResponse()

        // When
        mViewModel.handleResponseGetPhotos(response)

        // Then
        verify { mViewModel.handleResponseGetPhotos(any()) }
        verify(exactly = 1) { mViewModel.showLoading(false) }
    }

    private fun getMockAlbums(): ArrayList<Album> {
        val albumId = fake.anInt()

        return arrayListOf(
            spyk(
                Album(
                    userId = fake.anInt(),
                    id = albumId,
                    title = fake.aString(),
                    photos = arrayListOf(
                        spyk(
                            Photo(
                                albumId = albumId,
                                id = fake.anInt(),
                                title = fake.aString(),
                                thumbnailUrl = fake.aUrl(),
                                url = fake.aUrl()
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `verify get photos is correct when response successful`() {
        // Inject
        val mockResponse = mockk<ArrayList<Photo>>()
        val response = mockSuccessResponse()

        // Given
        every { mViewModel.getPhotos() } just runs
        mViewModel.albums = getMockAlbums()
        every { mViewModel.convertPhotosResponse(any()) } returns mockResponse

        // When
        mViewModel.handleResponseGetAlbums(response)

        // Then
        mViewModel.convertPhotosResponse(response) shouldBe mockResponse
        mViewModel.getPhotosAlbumIndex shouldBe mViewModel.getPhotosAlbumIndex++
        verify { mViewModel.handleResponseGetAlbums(any()) }
    }
}