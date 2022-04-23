package com.ekades.poststest.features.users.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.poststest.R
import com.ekades.poststest.features.users.presentation.model.User
import com.ekades.poststest.lib.core.ui.extension.loadImageUrlWithPlaceHolder
import com.ekades.poststest.lib.core.ui.foundation.background.shadowMedium
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.poststest.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.poststest.lib.core.ui.foundation.spacing.Spacing
import com.ekades.poststest.lib.ui.component.base.ComponentState
import kotlinx.android.synthetic.main.component_user.view.*

class UserCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<UserCV.State>(context, attributeSet, defStyle) {

    init {
        View.inflate(context, R.layout.component_user, this)
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
        var user: User? = null
        var onClickListener: ((item: User?) -> Unit)? = null
    }

    override fun initState(): State = State()
    override fun render(state: State) {
        with(state) {
            state.user?.apply {
                nameTextView.text = name
                usernameTextView.text = login
                avatarImageView.loadImageUrlWithPlaceHolder(
                    photoUrl = avatarUrl ?: ""
                )
                userCompanyNameTextView.text = company
                addressTextView.text = location
                emailTextView.text = email
                setOnClickListener {
                    onClickListener?.invoke(this)
                }
            }
        }
    }
}

