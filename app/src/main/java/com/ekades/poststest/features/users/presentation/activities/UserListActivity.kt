package com.ekades.poststest.features.users.presentation.activities

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.observe
import com.ekades.poststest.R
import com.ekades.poststest.features.users.presentation.components.UserCV
import com.ekades.poststest.features.users.presentation.model.User
import com.ekades.poststest.features.users.presentation.viewModels.UserListViewModel
import com.ekades.poststest.features.users.presentation.viewModels.state.UsersVS
import com.ekades.poststest.lib.application.ui.CoreActivity
import com.ekades.poststest.lib.application.ApplicationProvider.context
import com.ekades.poststest.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.poststest.lib.core.ui.extension.infiniteScrollListener
import com.ekades.poststest.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.poststest.lib.core.ui.extension.newComponent
import com.ekades.poststest.lib.core.ui.foundation.component.Component
import com.ekades.poststest.lib.core.ui.foundation.component.Rectangle
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.poststest.lib.core.ui.foundation.container.LinearContainer
import com.ekades.poststest.lib.core.ui.foundation.spacing.Spacing
import com.ekades.poststest.lib.ui.asset.extension.dp
import com.ekades.poststest.lib.ui.component.loading.RectangleSkeletonCV
import com.ekades.poststest.ui.user.UserDetailActivity
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListActivity : CoreActivity<UserListViewModel>(UserListViewModel::class) {

    private val usersAdapter by lazy {
        usersRecyclerView
            ?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_user_list
    }

    override fun render() = launch(Dispatchers.Main) {
        setupRecyclerView()
        observeViewModel()
        viewModel.getPosts()
    }

    private fun setupRecyclerView() {
//        usersRecyclerView.infiniteScrollListener(
//            visibleThreshold = 10,
//            loadMoreListener = {
//                if (viewModel.isOnShowLoader().not()) {
//                    viewModel.maxOffset += 5
//                    viewModel.currentIndex++
//                    viewModel.getUserByCurrentIndex()
//                }
//            }
//        )
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this) { viewState ->
            when (viewState) {
                is UsersVS.AddUsers -> {
                    renderUserList(viewState.usersVM, false)
                }
                is UsersVS.ShowLoader -> {
                    renderUserList(viewModel.shownUsers, viewState.showLoader)
                }
                is UsersVS.Error -> {
                    Toast.makeText(this, viewState.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderUserList(usersList: List<User>, isShowLoading: Boolean) {
        val componentList: MutableList<Component<*>> = mutableListOf()

        if (usersList.isNotEmpty()) {
            componentList.addAll(getUserComponents(usersList))
        }

        if (isShowLoading) {
            componentList.addAll(getLoadingComponents())
        }

        usersAdapter?.diffCalculateAdapter(componentList)
    }

    private fun getUserComponents(usersList: List<User>): MutableList<Component<UserCV>> {
        return usersList.map {
            ConstraintContainer.newComponent({
                UserCV(context)
            }) {
                user = it
                onClickListener = { user ->
                }
            }.withIdentifier("${it.id}".hashCode().toLong())
        }.toMutableList()
    }

    private fun getLoadingComponents(): MutableList<Component<RectangleSkeletonCV>> {
        val component: MutableList<Component<RectangleSkeletonCV>> = arrayListOf()
        for (i in 0 until 2) {
            component.add(LinearContainer.newComponent({
                RectangleSkeletonCV(this)
            }) {
                height = 150.dp()
                componentMargin = Rectangle(
                    vertical = Spacing.x8,
                    horizontal = Spacing.x16
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }

    companion object {
        @JvmStatic
        fun newIntent(activity: Activity): Intent {
            return Intent(activity, UserListActivity::class.java)
        }
    }
}