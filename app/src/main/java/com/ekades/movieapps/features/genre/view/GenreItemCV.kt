package com.ekades.movieapps.features.genre.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.movieapps.R
import com.ekades.movieapps.features.genre.model.GenreModel
import com.ekades.movieapps.lib.core.ui.foundation.background.shadowLarge
import com.ekades.movieapps.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.movieapps.lib.core.ui.foundation.corner.CornerRadius
import com.ekades.movieapps.lib.core.ui.foundation.spacing.Spacing
import kotlinx.android.synthetic.main.cv_genre_item.view.*

class GenreItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<GenreItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var section: GenreModel? = null
        var onItemClickListener: ((Int, String) -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_genre_item, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setContainerMargin(
            left = Spacing.x8,
            right = Spacing.x8,
            top = Spacing.x12,
            bottom = Spacing.x4
        )
        setContainerPadding(vertical = Spacing.x24, horizontal = Spacing.x16)
        shadowLarge(CornerRadius.XLARGE)
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            section?.apply {
                titleTextView.text = name
                setOnClickListener {
                    onItemClickListener?.invoke(id, name)
                }
            }
        }
    }
}