package com.ekades.poststest.features.post.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ekades.poststest.features.post.domain.interactor.GetPostsInteractor
import com.ekades.poststest.features.post.presentation.viewModels.state.PostsVS
import com.ekades.poststest.features.post.presentation.model.mapper.PostVMMapper
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.lib.core.networkV2.interactor.Interactor
import com.ekades.poststest.lib.core.networkV2.utils.io
import com.ekades.poststest.lib.core.networkV2.utils.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainV2ViewModel(
    private val getPostsInteractor: GetPostsInteractor
) : BaseViewModel() {

    val viewState: LiveData<PostsVS> get() = mViewState
    private val mViewState = MutableLiveData<PostsVS>()

    private val mPostVMMapper by lazy { PostVMMapper() }

    fun getPosts() {
        viewModelScope.launch {
            mViewState.value = PostsVS.ShowLoader(true)
            try {
                io {
                    getPostsInteractor.execute(Interactor.None)
                        .collect {
                            ui { mViewState.value = PostsVS.AddPost(mPostVMMapper.map(it)) }
                        }
                }
            } catch (e: Exception) {
                ui { mViewState.value = PostsVS.Error(e.message) }
            }
            mViewState.value = PostsVS.ShowLoader(false)
        }
    }
}