package com.ekades.fcmpushnotification.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.fcmpushnotification.R
import com.ekades.fcmpushnotification.lib.core.ui.foundation.background.CornerBackgroundMedium
import com.ekades.fcmpushnotification.lib.core.ui.foundation.color.ColorPalette
import com.ekades.fcmpushnotification.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.fcmpushnotification.lib.core.ui.foundation.spacing.Spacing
import com.ekades.fcmpushnotification.lib.ui.component.base.ComponentState
import com.ekades.fcmpushnotification.models.Comment
import kotlinx.android.synthetic.main.component_comment.view.*
import kotlinx.android.synthetic.main.component_comment.view.bodyTextView

class CommentCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<CommentCV.State>(context, attributeSet, defStyle) {

    init {
        View.inflate(context, R.layout.component_comment, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setContainerMargin(
            vertical = Spacing.x8,
            horizontal = Spacing.x4
        )
        setContainerPadding(
            vertical = Spacing.x0,
            horizontal = Spacing.x0
        )
        commentBgView.background = CornerBackgroundMedium().apply {
            setColor(ColorPalette.ALTO)
        }
    }

    class State : ComponentState() {
        var comment: Comment? = null
    }

    override fun initState(): State = State()
    override fun render(state: State) {
        state.comment?.apply {
            bodyTextView.text = body
            authorNameTextView.text = name
        }
    }
}

