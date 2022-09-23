package com.ekades.fcmpushnotification.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.fcmpushnotification.R
import com.ekades.fcmpushnotification.lib.core.ui.extension.loadImageUrlWithPlaceHolder
import com.ekades.fcmpushnotification.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.fcmpushnotification.lib.core.ui.foundation.spacing.Spacing
import com.ekades.fcmpushnotification.lib.ui.component.base.ComponentState
import com.ekades.fcmpushnotification.models.Photo
import kotlinx.android.synthetic.main.component_thumbnail.view.*

class ThumbnailPhotoCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<ThumbnailPhotoCV.State>(context, attributeSet, defStyle) {

    init {
        View.inflate(context, R.layout.component_thumbnail, this)
        setContainerParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        setContainerMargin(
            left = Spacing.x0,
            right = Spacing.x16,
            top = Spacing.x4,
            bottom = Spacing.x16
        )
    }

    class State : ComponentState() {
        var photo: Photo? = null
        var onClickListener: ((item: Photo?) -> Unit)? = null
    }

    override fun initState(): State = State()
    override fun render(state: State) {
        state.photo?.apply {
            thumbnailImageView.loadImageUrlWithPlaceHolder(
                photoUrl = "$url.png"
            )
            setOnClickListener {
                state.onClickListener?.invoke(this)
            }
        }
    }
}

