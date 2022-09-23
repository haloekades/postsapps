package com.ekades.fcmpushnotification.test.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseViewModelTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.models.Company
import com.ekades.fcmpushnotification.models.Post
import com.ekades.fcmpushnotification.models.User
import com.ekades.fcmpushnotification.ui.main.MainViewModel
import io.mockk.*
import org.junit.Rule
import org.junit.Test

class MainViewModelTest : BaseViewModelTest<MainViewModel>() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    override fun setup() {
        super.setup()
        mViewModel = spyk()
    }

    @Test
    fun `verify that error message is set correctly message when API get posts response is error`() {
        // Inject
        val response = mockErrorResponse()

        // When
        mViewModel.handleResponseGetPosts(response)

        // Then
        verify { mViewModel.handleResponseGetPosts(any()) }
        verify(exactly = 1) { mViewModel.showLoading(false) }
    }

    @Test
    fun `verify get posts is correct when response successful`() {
        // Inject
        val mockResponse = mockk<ArrayList<Post>>()
        val response = mockSuccessResponse()

        // Given
        every { mViewModel.convertPostsResponse(any()) } returns mockResponse

        // When
        mViewModel.handleResponseGetPosts(response)

        // Then
        mViewModel.convertPostsResponse(response) shouldBe mockResponse
        mViewModel.posts.value shouldBe mockResponse
        verify { mViewModel.handleResponseGetPosts(any()) }
    }

    @Test
    fun `verify that error message is set correctly message when API get users response is error`() {
        // Inject
        val response = mockErrorResponse()

        // When
        mViewModel.handleResponseGetUsers(response)

        // Then
        verify { mViewModel.handleResponseGetUsers(any()) }
        verify(exactly = 1) { mViewModel.showLoading(false) }
    }

    @Test
    fun `verify get users is correct when response successful`() {
        // Inject
        val mockResponse = mockk<ArrayList<User>>()
        val response = mockSuccessResponse()

        // Given
        every { mViewModel.convertUsersResponse(any()) } returns mockResponse

        // When
        mViewModel.handleResponseGetUsers(response)

        // Then
        mViewModel.convertUsersResponse(response) shouldBe mockResponse
        mViewModel.users.value shouldBe mockResponse
        verify { mViewModel.handleResponseGetUsers(any()) }
    }

    @Test
    fun `verify mediatorPostUsers when has data posts & users is correctly`() {
        // Inject
        val userId = fake.anInt()
        val mockPost = spyk(
            Post(
                userId = userId,
                id = fake.anInt(),
                title = fake.aString(),
                body = fake.aString(),
                userName = fake.aString(),
                userCompanyName = fake.aString()
            )
        )
        val mockUser = spyk(
            User(
                id = userId,
                name = fake.aString(),
                username = fake.aString(),
                email = fake.aString(),
                address = mockk(),
                phone = fake.aString(),
                website = fake.aString(),
                company = spyk(
                    Company(
                        name = fake.aString(),
                        catchPhrase = fake.aString(),
                        bs = fake.aString()
                    )
                )
            )
        )
        val mockObserver: Observer<ArrayList<Post>> = spyk()

        // Given
        mViewModel.mediatorPostUsers.observeForever(mockObserver)

        // When
        mViewModel.users.value = arrayListOf(mockUser)
        mViewModel.posts.value = arrayListOf(mockPost)

        mViewModel.mediatorPostUsers.value?.getOrNull(0)?.userId shouldBe userId
    }
}