package com.ekades.poststest.features.prayerschedule.searchcity.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.poststest.R
import com.ekades.poststest.features.prayerschedule.searchcity.model.CityItem
import com.ekades.poststest.lib.core.ui.extension.capitalizeWords
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import com.ekades.poststest.lib.ui.component.misc.DividerCV
import kotlinx.android.synthetic.main.cv_prayer_list_item.view.*

class CityListItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<CityListItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var city: CityItem? = null
        var onItemClickListener: ((cityId: String, cityName: String) -> Unit)? = null
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
            city?.apply {
                nameTextView.text = lokasi.capitalizeWords
                setOnClickListener {
                    onItemClickListener?.invoke(id, lokasi)
                }
            }
        }
    }
}