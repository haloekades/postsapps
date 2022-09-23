package com.ekades.fcmpushnotification.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.fcmpushnotification.R
import com.ekades.fcmpushnotification.lib.core.ui.foundation.background.CornerBackgroundFullRounded
import com.ekades.fcmpushnotification.lib.core.ui.foundation.background.shadowMedium
import com.ekades.fcmpushnotification.lib.core.ui.foundation.color.ColorPalette
import com.ekades.fcmpushnotification.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.fcmpushnotification.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.fcmpushnotification.lib.core.ui.foundation.spacing.Spacing
import com.ekades.fcmpushnotification.lib.ui.component.base.ComponentState
import com.ekades.fcmpushnotification.models.Post
import kotlinx.android.synthetic.main.component_main_post.view.*

class MainPostCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<MainPostCV.State>(context, attributeSet, defStyle) {

    init {
        View.inflate(context, R.layout.component_main_post, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setContainerMargin(
            horizontal = Spacing.x8,
            vertical = Spacing.x4
        )
        setContainerPadding(
            horizontal = Spacing.x16,
            vertical = Spacing.x16
        )
        shadowMedium(
            cornerRadius = CornerRadius.MEDIUM
        )
    }

    class State : ComponentState() {
        var post: Post? = null
        var onClickListener: ((item: Post?) -> Unit)? = null
    }

    override fun initState(): State = State()
    override fun render(state: State) {
        with(state) {
            state.post?.apply {
                titleTextView.text = title
                bodyTextView.text = body
                userNameTextView.text = userName ?: "-"
                renderUserIcon(userName ?: "-")
                userCompanyNameTextView.text = userCompanyName
                setOnClickListener {
                    onClickListener?.invoke(this)
                }
            }
        }
    }

    private fun renderUserIcon(userName: String) {
        userIconBgView.background = CornerBackgroundFullRounded(Spacing.x42.value).apply {
            setColor(ColorPalette.FRINGY_FLOWER)
        }
        userIconTextView.text = userName.getOrNull(0)?.toString()
    }
}

