package com.ekades.temandoa.lib.core.test

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


/**
 * @sample onView(withId(R.id.image_view)).perform(setVisible(true)));
 */
fun setVisible(value: Boolean): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(View::class.java)
        }
        
        override fun perform(uiController: UiController?, view: View) {
            view.visibility = if (value) View.VISIBLE else View.GONE
        }
        
        override fun getDescription(): String {
            return "Show / Hide View"
        }
    }
}

/**
 * @sample onView(withId(R.id.image_view)).check(matches(withDrawable(R.drawable.icon_1)));
 */
fun withDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("ImageView with drawable same as drawable with id $id")
    }
    
    override fun matchesSafely(view: View): Boolean {
        val context = view.context
        val expectedBitmap = ContextCompat.getDrawable(context, id)?.toBitmap()
        
        return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
    }
}

/**
 * @sample onView(withId(R.id.my_list_recycler)).check(matches(recyclerViewListSize(4)));
 */
fun withListSize(matcherSize: Int): Matcher<View?> {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with list size: $matcherSize")
        }
        
        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            return matcherSize == recyclerView.adapter!!.itemCount
        }
    }
}

/**
 * @sample onView(withId(R.id.recyclerview)).check(matches(onViewAtRecyclerViewPosition(0, withText(TEXT), R.id.text_view1)));
 */
fun onViewAtRecyclerViewPosition(position: Int, itemMatcher: Matcher<View?>, @NonNull targetViewId: Int): Matcher<View?> {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has view id $itemMatcher at position $position")
        }
        
        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            val targetView = viewHolder?.itemView?.findViewById<View>(targetViewId)
            return itemMatcher.matches(targetView)
        }
    }
}

/**
 * @sample onView(withId(R.id.text)).check(matches(withEditTextHint(HINT)));
 */
fun withHint(matcherText: String): Matcher<View?> {
    return object : BoundedMatcher<View?, EditText>(EditText::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with item hint: $matcherText")
        }
        
        override fun matchesSafely(editTextField: EditText): Boolean {
            return matcherText.equals(editTextField.hint.toString(), ignoreCase = true)
        }
    }
}

/**
 * @sample onView(withId(R.id.text_view1)).check(matches(withTextColor(ColorPalette.TUNDORA)));
 */
fun withTextColor(@ColorInt color: Int): Matcher<View?> {
    return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with text color: $color")
        }
        
        override fun matchesSafely(textView: TextView): Boolean {
            return color == textView.currentTextColor
        }
    }
}

fun withSize(expectedWidth: Int, expectedHeight: Int): TypeSafeMatcher<View?> {
    return object : TypeSafeMatcher<View?>(View::class.java) {
        override fun matchesSafely(target: View?): Boolean {
            val targetWidth = target?.layoutParams?.width
            val targetHeight = target?.layoutParams?.height
            return targetWidth == expectedWidth && targetHeight == expectedHeight
        }

        override fun describeTo(description: Description) {
            description.appendText("with view size: ")
            description.appendValue(expectedWidth.toString() + "x" + expectedHeight)
        }
    }
}

fun withFontSize(expectedSize: Float): TypeSafeMatcher<View?> {
    return object : TypeSafeMatcher<View?>(View::class.java) {
        override fun matchesSafely(target: View?): Boolean {
            if (target !is TextView) {
                return false
            }
            return target.textSize == expectedSize
        }

        override fun describeTo(description: Description) {
            description.appendText("with fontSize: ")
            description.appendValue(expectedSize)
        }
    }
}

class ScrollToAction(
    private val original: androidx.test.espresso.action.ScrollToAction = androidx.test.espresso.action.ScrollToAction()
) : ViewAction by original {

    override fun getConstraints(): Matcher<View> = anyOf(
        allOf(
            withEffectiveVisibility(Visibility.VISIBLE),
            isDescendantOfA(isAssignableFrom(NestedScrollView::class.java))
        ),
        original.constraints
    )
}