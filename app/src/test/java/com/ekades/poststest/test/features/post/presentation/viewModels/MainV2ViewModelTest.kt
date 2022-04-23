package com.ekades.poststest.test.features.post.presentation.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ekades.poststest.features.post.domain.interactor.GetPostsInteractor
import com.ekades.poststest.features.post.presentation.viewModels.MainV2ViewModel
import com.ekades.poststest.features.post.presentation.viewModels.state.PostsVS
import com.ekades.poststest.lib.core.networkV2.interactor.Interactor
import com.ekades.poststest.test.utils.CoroutinesRule
import com.ekades.poststest.test.utils.TestData
import com.ekades.poststest.test.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainV2ViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutinesRule()

    private val useCaseMock = mockk<GetPostsInteractor>()
    private lateinit var viewModel: MainV2ViewModel
    private val observer = mockk<Observer<PostsVS>>(relaxed = true)

    @Before
    fun setup() {
        viewModel = MainV2ViewModel(useCaseMock)
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `retrieve posts successful`() {
        coEvery { useCaseMock.execute(Interactor.None) } returns TestData.dataList
        viewModel.getPosts()
        viewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<PostsVS.AddPost>()) }
    }

    @Test
    fun `retrieve posts failure`() {
        coEvery { useCaseMock.execute(Interactor.None) } throws UnsupportedOperationException()
        viewModel.getPosts()
        viewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<PostsVS.Error>()) }
    }

    @Suppress("UNREACHABLE_CODE")
    @Test(expected = UnsupportedOperationException::class)
    fun `retrieve posts with exception`() {
        coEvery { useCaseMock.execute(Interactor.None) } returns throw UnsupportedOperationException()
        viewModel.getPosts()
    }
}