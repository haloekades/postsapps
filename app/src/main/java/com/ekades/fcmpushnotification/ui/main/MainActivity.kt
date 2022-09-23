package com.ekades.fcmpushnotification.ui.main

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import com.ekades.fcmpushnotification.R
import com.ekades.fcmpushnotification.components.MainPostCV
import com.ekades.fcmpushnotification.lib.application.ui.CoreActivity
import com.ekades.fcmpushnotification.lib.application.ApplicationProvider.context
import com.ekades.fcmpushnotification.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.fcmpushnotification.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.fcmpushnotification.lib.core.ui.extension.newComponent
import com.ekades.fcmpushnotification.lib.core.ui.foundation.color.ColorPalette
import com.ekades.fcmpushnotification.lib.core.ui.foundation.component.Component
import com.ekades.fcmpushnotification.lib.core.ui.foundation.component.Rectangle
import com.ekades.fcmpushnotification.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.fcmpushnotification.lib.core.ui.foundation.container.LinearContainer
import com.ekades.fcmpushnotification.lib.core.ui.foundation.spacing.Spacing
import com.ekades.fcmpushnotification.lib.ui.asset.extension.dp
import com.ekades.fcmpushnotification.models.Post
import com.ekades.fcmpushnotification.ui.post.PostDetailActivity
import com.ekades.fcmpushnotification.lib.ui.component.loading.RectangleSkeletonCV
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.backgroundColor

class MainActivity : CoreActivity<MainViewModel>(MainViewModel::class) {

    private val postsAdapter by lazy {
        postsRecyclerView?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_main
    }

    override fun render() = launch(Dispatchers.Main) {
        setupToolbarView()
        registerObserver()
        getMainData()
    }

    private fun setupToolbarView() {
        toolbar.title = getString(R.string.title_app_name)
        toolbar.setTitleTextColor(ColorPalette.WHITE)
        toolbar.backgroundColor = ColorPalette.BRAND
        setSupportActionBar(toolbar);
    }

    private fun getMainData() {
        showLoadingView()
        viewModel.getPostList()
        viewModel.getUserList()
    }

    private fun registerObserver() {
        val postUsers = viewModel.mediatorPostUsers
        postUsers.observe(this, object : Observer<ArrayList<Post>> {
            override fun onChanged(postList: ArrayList<Post>) {
                postUsers.removeObserver(this)
                postList.bindView()
            }
        })

        viewModel.getPostsApiResponse.observe(this, Observer {
            viewModel.handleResponseGetPosts(it)
        })

        viewModel.getUsersApiResponse.observe(this, Observer {
            viewModel.handleResponseGetUsers(it)
        })
    }

    private fun showLoadingView() {
        val loadingComponents: MutableList<Component<*>> = mutableListOf()
        loadingComponents.addAll(getLoadingComponents())
        postsAdapter?.setNewList(loadingComponents)
    }

    private fun getLoadingComponents(): MutableList<Component<RectangleSkeletonCV>> {
        val component: MutableList<Component<RectangleSkeletonCV>> = arrayListOf()
        for (i in 0 until 2) {
            component.add(LinearContainer.newComponent({
                RectangleSkeletonCV(this)
            }) {
                height = 300.dp()
                componentMargin = Rectangle(
                    vertical = Spacing.x8,
                    horizontal = Spacing.x16
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }

    private fun ArrayList<Post>.bindView() {
        val postsComponent: MutableList<Component<*>> =
            this.map {
                ConstraintContainer.newComponent({
                    MainPostCV(context)
                }) {
                    post = it
                    onClickListener = { itemPost ->
                        openPostDetail(itemPost)
                    }
                }.withIdentifier("${it.id}".hashCode().toLong())
            }.toMutableList()

        postsAdapter?.diffCalculateAdapter(postsComponent)
    }

    private fun openPostDetail(post: Post?) {
        post?.apply {
            startActivity(
                PostDetailActivity.newIntent(
                    this@MainActivity,
                    post = post,
                    user = viewModel.getUserById(post.userId)
                )
            )
        }
    }

    companion object {
        @JvmStatic
        fun newIntent(activity: Activity): Intent {
            return Intent(activity, MainActivity::class.java)
        }
    }
}