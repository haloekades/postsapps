package com.ekades.poststest.features.prayerschedule.prayerscheduledetail.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekades.poststest.R
import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.model.ScheduleItem
import com.ekades.poststest.lib.core.ui.foundation.container.ConstraintContainer
import kotlinx.android.synthetic.main.cv_schedule_item.view.*
import org.jetbrains.anko.backgroundColor

class ScheduleItemCV @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintContainer<ScheduleItemCV.State>(context, attributeSet, defStyle) {

    class State {
        var scheduleItem: ScheduleItem? = null
    }

    init {
        View.inflate(context, R.layout.cv_schedule_item, this)
        setContainerParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun initState(): State = State()

    override fun render(state: State) {
        with(state) {
            scheduleItem?.apply {
                nameTextView.text = name
                timeTextView.text = time
                backgroundColor = bgColor
            }
        }
    }
}