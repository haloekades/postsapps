package com.ekades.poststest.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ekades.poststest.R
import com.ekades.poststest.lib.application.ApplicationProvider
import com.ekades.poststest.lib.core.ui.extension.diffCalculateAdapter
import com.ekades.poststest.lib.core.ui.extension.linearLayoutAdapter
import com.ekades.poststest.lib.core.ui.extension.newComponent
import com.ekades.poststest.lib.core.ui.foundation.component.Component
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.poststest.lib.core.ui.foundation.spacing.Spacing
import com.ekades.poststest.lib.ui.component.base.ComponentState
import com.ekades.poststest.lib.ui.component.misc.DividerCV
import com.ekades.poststest.models.Album
import com.ekades.poststest.models.Photo
import kotlinx.android.synthetic.main.component_album.view.*

class AlbumCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<AlbumCV.State>(context, attributeSet, defStyle) {

    private val photosAdapter by lazy {
        photosRecyclerView?.linearLayoutAdapter(context, RecyclerView.HORIZONTAL)
    }

    init {
        View.inflate(context, R.layout.component_album, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setContainerMargin(
            horizontal = Spacing.x0,
            vertical = Spacing.x8
        )
    }

    class State : ComponentState() {
        var album: Album? = null
        var onClickListener: ((item: Photo?) -> Unit)? = null
    }

    override fun initState(): State = State()
    override fun render(state: State) {
        state.album?.apply {
            albumTextView.text = title
            dividerCV.bind { dividerStyle = DividerCV.DividerStyle.STRAIGHT }
            photos?.apply {
                bindThumbnailPhotosView(onClickListener = state.onClickListener)
            }
        }
    }

    private fun ArrayList<Photo>.bindThumbnailPhotosView(
        onClickListener: ((item: Photo?) -> Unit)?
    ) {
        val albumsComponent: MutableList<Component<*>> =
            this.map {
                ConstraintContainer.newComponent({
                    ThumbnailPhotoCV(context)
                }) {
                    photo = it
                    this.onClickListener = onClickListener
                }.withIdentifier("${it.id}".hashCode().toLong())
            }.toMutableList()

        photosAdapter?.diffCalculateAdapter(albumsComponent)
    }
}

