package com.ekades.poststest.ui.post

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import com.ekades.poststest.R
import com.ekades.poststest.components.CommentCV
import com.ekades.poststest.lib.application.ui.CoreActivity
import com.ekades.poststest.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.poststest.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.poststest.lib.core.ui.extension.newComponent
import com.ekades.poststest.lib.core.ui.foundation.background.CornerBackgroundFullRounded
import com.ekades.poststest.lib.core.ui.foundation.color.ColorPalette
import com.ekades.poststest.lib.core.ui.foundation.component.Component
import com.ekades.poststest.lib.core.ui.foundation.component.Rectangle
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.poststest.lib.core.ui.foundation.container.LinearContainer
import com.ekades.poststest.lib.core.ui.foundation.spacing.Spacing
import com.ekades.poststest.lib.ui.asset.extension.dp
import com.ekades.poststest.lib.ui.component.misc.DividerCV
import com.ekades.poststest.models.Comment
import com.ekades.poststest.models.Post
import com.ekades.poststest.models.User
import com.ekades.poststest.ui.user.UserDetailActivity
import com.ekades.poststest.lib.ui.component.loading.RectangleSkeletonCV
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.activity_post_detail.toolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.backgroundColor

class PostDetailActivity : CoreActivity<PostDetailViewModel>(PostDetailViewModel::class) {

    private val commentsAdapter by lazy {
        commentRecyclerView?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_post_detail
    }

    override fun render() = launch(Dispatchers.Main) {
        viewModel.processIntent(intent)
        setupToolbarView()
        registerObserver()
        setupListener()
        bindDividerCV()
        bindPostUserView()
        viewModel.getCommentList()
    }

    private fun setupToolbarView() {
        toolbar.title = getString(R.string.title_post_detail)
        toolbar.setTitleTextColor(ColorPalette.WHITE)
        toolbar.backgroundColor = ColorPalette.BRAND
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun bindDividerCV() {
        dividerCV.bind { dividerStyle = DividerCV.DividerStyle.STRAIGHT }
    }

    private fun registerObserver() {
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                showLoadingCommentsView()
            }
        })

        viewModel.getCommentsApiResponse.observe(this, Observer {
            viewModel.handleResponseGetComments(it)
        })

        viewModel.comments.observe(this, Observer { comments ->
            comments.bindCommentView()
        })
    }

    private fun setupListener() {
        userNameTextView.setOnClickListener {
            openUserDetail()
        }

        userIconBgView.setOnClickListener {
            openUserDetail()
        }

        userCompanyNameTextView.setOnClickListener {
            openUserDetail()
        }
    }

    private fun openUserDetail() {
        viewModel.user?.apply {
            startActivity(UserDetailActivity.newIntent(this@PostDetailActivity, this))
        }
    }

    private fun bindPostUserView() {
        renderUserIcon(viewModel.user?.username ?: "-")
        userNameTextView.text = viewModel.user?.username ?: "-"
        userCompanyNameTextView.text = viewModel.user?.company?.name ?: "-"
        titleTextView.text = viewModel.post?.title ?: "-"
        bodyTextView.text = viewModel.post?.body ?: "-"
    }

    private fun renderUserIcon(userName: String) {
        userIconBgView.background = CornerBackgroundFullRounded(Spacing.x42.value).apply {
            setColor(ColorPalette.FRINGY_FLOWER)
        }
        userIconTextView.text = userName.getOrNull(0)?.toString()
    }

    private fun showLoadingCommentsView() {
        val loadingComponents: MutableList<Component<*>> = mutableListOf()
        loadingComponents.addAll(getLoadingComponents())
        commentsAdapter?.setNewList(loadingComponents)
    }

    private fun getLoadingComponents(): MutableList<Component<RectangleSkeletonCV>> {
        val component: MutableList<Component<RectangleSkeletonCV>> = arrayListOf()
        for (i in 0 until 2) {
            component.add(LinearContainer.newComponent({
                RectangleSkeletonCV(this)
            }) {
                height = 200.dp()
                componentMargin = Rectangle(
                    top = Spacing.x8,
                    left = Spacing.x20,
                    right = Spacing.x16,
                    bottom = Spacing.x8
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }

    private fun ArrayList<Comment>?.bindCommentView() {
        this?.apply {
            val postsComponent: MutableList<Component<*>> =
                this.map {
                    ConstraintContainer.newComponent({
                        CommentCV(this@PostDetailActivity)
                    }) {
                        comment = it
                    }.withIdentifier("${it.id}".hashCode().toLong())
                }.toMutableList()

            commentsAdapter?.diffCalculateAdapter(postsComponent)
        }
    }

    companion object {
        const val EXTRA_POST = "extra_post"
        const val EXTRA_USER = "extra_user"

        @JvmStatic
        fun newIntent(activity: Activity, post: Post, user: User?): Intent {
            return Intent(activity, PostDetailActivity::class.java).apply {
                putExtra(EXTRA_POST, post)
                user?.apply {
                    putExtra(EXTRA_USER, this@apply)
                }
            }
        }
    }
}