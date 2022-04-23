package com.ekades.poststest.features.users.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.carlosgub.coroutines.features.books.domain.interactor.GetUserByLoginInteractor
import com.ekades.poststest.features.users.domain.interactor.GetUsersInteractor
import com.ekades.poststest.features.users.presentation.model.User
import com.ekades.poststest.features.users.presentation.model.mapper.UserVMMapper
import com.ekades.poststest.features.users.presentation.viewModels.state.UsersVS
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.lib.core.networkV2.interactor.Interactor
import com.ekades.poststest.lib.core.networkV2.utils.io
import com.ekades.poststest.lib.core.networkV2.utils.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserListViewModel(
    private val getUsersInteractor: GetUsersInteractor,
    private val getUserByLoginInteractor: GetUserByLoginInteractor
) : BaseViewModel() {

    val viewState: LiveData<UsersVS> get() = mViewState
    private val mViewState = MutableLiveData<UsersVS>()
    val shownUsers = arrayListOf<User>()
    private val mUsers = arrayListOf<User>()
    var currentIndex = 0
    var maxOffset = 5

    private val mUserVMMapper by lazy { UserVMMapper() }

    fun getPosts() {
        viewModelScope.launch {
            mViewState.value = UsersVS.ShowLoader(true)
            try {
                io {
                    getUsersInteractor.execute(Interactor.None)
                        .collect {
                            io {
                                mUsers.addAll(mUserVMMapper.map(it))
                                getUserByCurrentIndex()
                            }
                        }
                }
            } catch (e: Exception) {
                ui { mViewState.value = UsersVS.Error(e.message) }
                mViewState.value = UsersVS.ShowLoader(false)
            }
        }
    }

    private fun getUserByLogin(login: String) {
        viewModelScope.launch {
            if (isOnShowLoader().not()) {
                mViewState.value = UsersVS.ShowLoader(true)
            }
            try {
                io {
                    getUserByLoginInteractor.execute(GetUserByLoginInteractor.Params(login = login))
                        .collect {
                            ui {
                                mUserVMMapper.map(it).apply {
                                    shownUsers.add(this)
                                    if (currentIndex + 1 < maxOffset) {
                                        currentIndex++
                                        getUserByCurrentIndex()
                                    } else {
                                        mViewState.value = UsersVS.AddUsers(shownUsers)
                                    }
                                }
                            }
                        }
                }
            } catch (e: Exception) {
                ui {
                    mViewState.value = UsersVS.Error(e.message)
                    mViewState.value = UsersVS.ShowLoader(false)
                }
            }
        }
    }

    fun isOnShowLoader(): Boolean {
        return when (mViewState.value) {
            is UsersVS.ShowLoader -> (mViewState.value as UsersVS.ShowLoader).showLoader
            else -> false
        }
    }

    fun getUserByCurrentIndex() {
        mUsers.getOrNull(currentIndex)?.login?.apply {
            getUserByLogin(this)
        }
    }
}