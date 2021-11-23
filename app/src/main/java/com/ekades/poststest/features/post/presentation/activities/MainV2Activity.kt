package com.ekades.poststest.features.post.presentation.activities

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.observe
import com.ekades.poststest.R
import com.ekades.poststest.components.MainPostCV
import com.ekades.poststest.features.post.presentation.model.PostVM
import com.ekades.poststest.features.post.presentation.viewModels.MainV2ViewModel
import com.ekades.poststest.features.post.presentation.viewModels.state.PostsVS
import com.ekades.poststest.lib.application.ui.CoreActivity
import com.ekades.poststest.lib.application.ApplicationProvider.context
import com.ekades.poststest.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.poststest.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.poststest.lib.core.ui.extension.newComponent
import com.ekades.poststest.lib.core.ui.foundation.color.ColorPalette
import com.ekades.poststest.lib.core.ui.foundation.component.Component
import com.ekades.poststest.lib.core.ui.foundation.component.Rectangle
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.poststest.lib.core.ui.foundation.container.LinearContainer
import com.ekades.poststest.lib.core.ui.foundation.spacing.Spacing
import com.ekades.poststest.lib.ui.asset.extension.dp
import com.ekades.poststest.models.Post
import com.ekades.poststest.ui.post.PostDetailActivity
import com.ekades.poststest.lib.ui.component.loading.RectangleSkeletonCV
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.backgroundColor

class MainV2Activity : CoreActivity<MainV2ViewModel>(MainV2ViewModel::class) {

    private val postsAdapter by lazy {
        postsRecyclerView?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_main
    }

    override fun render() = launch(Dispatchers.Main) {
        setupToolbarView()
        observeViewModel()
        viewModel.getPosts()
    }

    private fun setupToolbarView() {
        toolbar.title = getString(R.string.title_app_name)
        toolbar.setTitleTextColor(ColorPalette.WHITE)
        toolbar.backgroundColor = ColorPalette.BRAND
        setSupportActionBar(toolbar);
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this) {
            when (it) {
                is PostsVS.AddPost -> {
//                    bindView(it.postVM)
//                    println(it.postsVM)
                    it.postsVM.bindView()
                }
                is PostsVS.ShowLoader -> {
                    if (it.showLoader) {
                        showLoadingView()
                    }
                }
                is PostsVS.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
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

//    private fun bindView(postVM: PostVM) {
//        val postsComponent: MutableList<Component<*>> =
//            postVM.map {
//                ConstraintContainer.newComponent({
//                    MainPostCV(context)
//                }) {
//                    post = it
//                    onClickListener = { itemPost ->
//                        openPostDetail(itemPost)
//                    }
//                }.withIdentifier("${it.id}".hashCode().toLong())
//            }.toMutableList()
//
//        postsAdapter?.diffCalculateAdapter(postsComponent)
//    }
//
    private fun List<Post>.bindView() {
        val postsComponent: MutableList<Component<*>> =
            this.map {
                ConstraintContainer.newComponent({
                    MainPostCV(context)
                }) {
                    post = it
                    onClickListener = { itemPost ->
//                        openPostDetail(itemPost)
                    }
                }.withIdentifier("${it.id}".hashCode().toLong())
            }.toMutableList()

        postsAdapter?.diffCalculateAdapter(postsComponent)
    }
//
//    private fun openPostDetail(post: Post?) {
//        post?.apply {
//            startActivity(
//                PostDetailActivity.newIntent(
//                    this@MainV2Activity,
//                    post = post,
//                    user = viewModel.getUserById(post.userId)
//                )
//            )
//        }
//    }

    companion object {
        @JvmStatic
        fun newIntent(activity: Activity): Intent {
            return Intent(activity, MainV2Activity::class.java)
        }
    }
}