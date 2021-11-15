package com.ekades.poststest.test.ui.post

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ekades.poststest.lib.core.test.viewmodels.base.BaseViewModelTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import com.ekades.poststest.models.Comment
import com.ekades.poststest.models.Post
import com.ekades.poststest.models.User
import com.ekades.poststest.ui.post.PostDetailActivity
import com.ekades.poststest.ui.post.PostDetailViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class PostDetailViewModelTest : BaseViewModelTest<PostDetailViewModel>() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    override fun setup() {
        super.setup()
        mViewModel = spyk()
    }

    @Test
    fun `verify process intent is correctly`() {
        // Inject
        val mockPost = mockk<Post>()
        val mockUser = mockk<User>()
        val mockIntent: Intent = mockk(relaxed = true)

        // Given
        every { mockIntent.hasExtra(PostDetailActivity.EXTRA_POST) } returns true
        every { mockIntent.getParcelableExtra<Post>(PostDetailActivity.EXTRA_POST) } returns mockPost
        every { mockIntent.hasExtra(PostDetailActivity.EXTRA_USER) } returns true
        every { mockIntent.getParcelableExtra<User>(PostDetailActivity.EXTRA_USER) } returns mockUser

        // When
        mViewModel.processIntent(mockIntent)

        // Then
        mViewModel.post shouldBe mockPost
        mViewModel.user shouldBe mockUser
    }

    @Test
    fun `verify that loading is called while API get comments response is loading`() {
        // Given
        val response = mockLoadingResponse()

        // When
        mViewModel.handleResponseGetComments(response)

        // Then
        verify(exactly = 1) { mViewModel.showLoading() }
    }

    @Test
    fun `verify that error message is set correctly message when API get comments response is error`() {
        // Inject
        val response = mockErrorResponse()

        // When
        mViewModel.handleResponseGetComments(response)

        // Then
        verify { mViewModel.handleResponseGetComments(any()) }
        verify(exactly = 1) { mViewModel.showLoading(false) }
    }

    @Test
    fun `verify get posts is correct when response successful`() {
        // Inject
        val mockResponse = mockk<ArrayList<Comment>>()
        val response = mockSuccessResponse()

        // Given
        every { mViewModel.convertCommentsResponse(any()) } returns mockResponse

        // When
        mViewModel.handleResponseGetComments(response)

        // Then
        mViewModel.convertCommentsResponse(response) shouldBe mockResponse
        mViewModel.comments.value shouldBe mockResponse
        verify { mViewModel.handleResponseGetComments(any()) }
    }

}