package com.ekades.movieapps.features.prayerlist.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.movieapps.R
import com.ekades.movieapps.features.prayerdetail.model.PrayerItem
import com.ekades.movieapps.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.movieapps.lib.ui.component.misc.DividerCV
import kotlinx.android.synthetic.main.cv_prayer_list_item.view.*

class PrayerListItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<PrayerListItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var section: PrayerItem? = null
        var onItemClickListener: (() -> Unit)? = null
    }

    init {
        View.inflate(context, R.layout.cv_prayer_list_item, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        dividerCV.bind {
            dividerStyle = DividerCV.DividerStyle.STRAIGHT
        }
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            section?.apply {
                nameTextView.text = name
                setOnClickListener {
                    onItemClickListener?.invoke()
                }
            }
        }
    }
}