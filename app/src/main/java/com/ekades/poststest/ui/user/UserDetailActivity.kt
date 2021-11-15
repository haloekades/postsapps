package com.ekades.poststest.ui.user

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.lifecycle.Observer
import com.ekades.poststest.R
import com.ekades.poststest.components.AlbumCV
import com.ekades.poststest.lib.application.ui.CoreActivity
import com.ekades.poststest.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.poststest.lib.core.ui.extension.infiniteScrollListener
import com.ekades.poststest.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.poststest.lib.core.ui.extension.newComponent
import com.ekades.poststest.lib.core.ui.foundation.color.ColorPalette
import com.ekades.poststest.lib.core.ui.foundation.component.Component
import com.ekades.poststest.lib.core.ui.foundation.component.Rectangle
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.poststest.lib.core.ui.foundation.container.LinearContainer
import com.ekades.poststest.lib.core.ui.foundation.spacing.Spacing
import com.ekades.poststest.lib.ui.asset.extension.dp
import com.ekades.poststest.lib.ui.component.misc.DividerCV
import com.ekades.poststest.models.Album
import com.ekades.poststest.models.Photo
import com.ekades.poststest.models.User
import com.ekades.poststest.ui.photo.PhotoDetailActivity
import com.ekades.poststest.ui.post.PostDetailActivity
import com.ekades.poststest.lib.ui.component.loading.RectangleSkeletonCV
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.activity_user_detail.toolbar
import kotlinx.android.synthetic.main.activity_user_detail.userNameTextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.backgroundColor

class UserDetailActivity : CoreActivity<UserDetailViewModel>(UserDetailViewModel::class) {

    private val albumAdapter by lazy {
        albumRecyclerView?.linearLayoutAdapter(this)
    }

    init {
        activityLayoutRes = R.layout.activity_user_detail
    }

    override fun render() = launch(Dispatchers.Main) {
        setupRecyclerView()
        setupToolbarView()
        viewModel.processIntent(intent)
        registerObserver()
        bindDividerCV()
        bindUserView()
        viewModel.getAlbumList()
    }

    private fun setupToolbarView() {
        toolbar.title = getString(R.string.title_user_detail)
        toolbar.setTitleTextColor(ColorPalette.WHITE)
        toolbar.backgroundColor = ColorPalette.BRAND
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        albumRecyclerView.infiniteScrollListener(
            visibleThreshold = VISIBLE_THRESHOLD,
            loadMoreListener = {
                if (viewModel.maxAlbumIndex == viewModel.getPhotosAlbumIndex) {
                    viewModel.maxAlbumIndex += 2
                    viewModel.getPhotosAlbumIndex++
                    viewModel.getPhotos()
                }
            }
        )
    }

    private fun registerObserver() {
        viewModel.isLoading.observe(this, Observer { isShowLoading ->
            if (isShowLoading == true) {
                bindAlbumView(viewModel.userAlbums.value, true)
            }
        })

        viewModel.getAlbumsApiResponse.observe(this, Observer {
            viewModel.handleResponseGetAlbums(it)
        })

        viewModel.getPhotosApiResponse.observe(this, Observer {
            viewModel.handleResponseGetPhotos(it)
        })

        viewModel.userAlbums.observe(this, Observer {
            it?.apply {
                bindAlbumView(this)
            }
        })
    }

    private fun bindDividerCV() {
        dividerUserName.bind { dividerStyle = DividerCV.DividerStyle.STRAIGHT }
        dividerEmail.bind { dividerStyle = DividerCV.DividerStyle.STRAIGHT }
        dividerAddress.bind { dividerStyle = DividerCV.DividerStyle.STRAIGHT }
        dividerCompany.bind { dividerStyle = DividerCV.DividerStyle.STRAIGHT }
    }

    private fun bindUserView() {
        userNameTextView.setHtmlText(
            getString(
                R.string.username_with_value,
                viewModel.user?.username ?: "-"
            )
        )
        emailTextView.setHtmlText(
            getString(
                R.string.email_with_value,
                viewModel.user?.email ?: "-"
            )
        )
        bindAddressView()
        bindCompanyView()
    }

    private fun bindAddressView() {
        val address = viewModel.user?.address

        address?.apply {
            addressTextView.setHtmlText(
                getString(
                    R.string.address_with_value,
                    street, suite, city, zipcode
                )
            )
        }
    }

    private fun bindCompanyView() {
        val company = viewModel.user?.company

        company?.apply {
            companyTextView.setHtmlText(
                getString(
                    R.string.company_with_value,
                    name,
                    catchPhrase,
                    bs
                )
            )
        }
    }

    private fun TextView.setHtmlText(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            this.text = Html.fromHtml(text)
        }
    }

    private fun bindAlbumView(albums: ArrayList<Album>?, isShowLoading: Boolean = false) {
        val components: MutableList<Component<*>> = arrayListOf()

        albums?.apply {
            components.addAll(getAlbumsComponent())
        }

        if (isShowLoading) {
            components.addAll(getLoadingComponents())
        }

        albumAdapter?.diffCalculateAdapter(components)
    }

    private fun ArrayList<Album>.getAlbumsComponent(): MutableList<Component<AlbumCV>> {
        return this.map {
            ConstraintContainer.newComponent({
                AlbumCV(this@UserDetailActivity)
            }) {
                album = it
                onClickListener = { photo ->
                    photo?.apply {
                        openPhotoDetail(this)
                    }
                }
            }.withIdentifier("${it.id}".hashCode().toLong())
        }.toMutableList()
    }

    private fun openPhotoDetail(photo: Photo) {
        startActivity(PhotoDetailActivity.newIntent(this, photo))
    }

    private fun getLoadingComponents(): MutableList<Component<RectangleSkeletonCV>> {
        val component: MutableList<Component<RectangleSkeletonCV>> = arrayListOf()
        for (i in 0 until 2) {
            component.add(LinearContainer.newComponent({
                RectangleSkeletonCV(this)
            }) {
                height = 100.dp()
                componentMargin = Rectangle(
                    vertical = Spacing.x8,
                    horizontal = Spacing.x0
                )
            }.setIdentifier("loading-$i"))
        }

        return component
    }

    companion object {
        const val VISIBLE_THRESHOLD = 10

        @JvmStatic
        fun newIntent(activity: Activity, user: User): Intent {
            return Intent(activity, UserDetailActivity::class.java).apply {
                putExtra(PostDetailActivity.EXTRA_USER, user)
            }
        }
    }
}