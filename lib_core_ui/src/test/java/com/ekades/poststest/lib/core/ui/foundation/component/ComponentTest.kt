package com.ekades.poststest.lib.core.ui.foundation.component

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ekades.poststest.lib.core.test.viewmodels.base.BaseTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ComponentTest : BaseTest() {
    lateinit var component: Component<View>

    private val context: Context = mockk(relaxed = true)
    private val mockView: View = mockk(relaxed = true)
    private val mockBinder: (View) -> Unit = {}
    private val mockUnbinder: (View) -> Unit = {}

    override fun setup() {
        super.setup()
        component = Component(1) {
            mockView
        }
    }

    @Test
    fun `Get component type returns correct type`() {
        component.getType() shouldBe component.type
    }

    @Test
    fun `Bind view works correctly when injected`() {
        // Inject
        val mockViewHolder = ComponentViewHolder<View>(mockView)

        // When
        component.onAttached {}
        component.bindView(mockViewHolder, mutableListOf())

        // Then
        component.mBinder.invoke(mockView) shouldBe mockBinder.invoke(mockView)
    }

    @Test
    fun `Unbind view works correctly when injected`() {
        // Inject
        val mockViewHolder = ComponentViewHolder<View>(mockView)

        // When
        component.onDetached {}
        component.unbindView(mockViewHolder)

        // Then
        component.mUnbinder.invoke(mockView) shouldBe mockUnbinder.invoke(mockView)
    }

    @Test
    fun `Generate view returns correct view`() {
        component.generateView(mockContext, LinearLayout(context)) shouldBe component.mGenerator.invoke(mockContext)
    }

    @Test
    fun `isExpanded returns correct mExpanded`() {
        component.isExpanded shouldBe component.mExpanded
    }

    @Test
    fun `mExpanded returns correct value when withIsExpanded is called`() {
        // When
        component.withIsExpanded(true)

        // Then
        component.mExpanded shouldBe true
    }

    @Test
    fun `Sub Items is correct when injected`() {
        // Inject
        val mockComponent = Component(1) {
            mockView
        }

        // When
        component.withSubItems(listOf(mockComponent))

        // Then
        component.mSubItems.size shouldBe 1
    }

    @Test
    fun `Sub Items returns mSubItems when called`() {
        component.subItems shouldBe component.mSubItems
    }

    @Test
    fun `is Auto Expanding returns true`() {
        component.isAutoExpanding shouldBe true
    }

    @Test
    fun `Get Parent Component returns mParent`() {
        component.parent shouldBe component.mParent
    }

    @Test
    fun `Set and Get Component Tag is working correctly when injected`() {
        val mockComponent = mockk<Component<*>>(relaxed = true)
        component.setTag(Component::class, mockComponent)

        component.mTypedTags.size shouldBe 1
        component.getTag(Component::class) shouldBe mockComponent
    }

    @Test
    fun `Get Component Layout Res always returns 0`() {
        component.layoutRes shouldBe 0
    }

    @Test
    fun `Get Component View Holder returns correct view holder`() {
        // Inject
        val mockView: ViewGroup = mockk(relaxed = true)

        // When
        component.getViewHolder(mockView)

        // Then
        verify(exactly = 1) { component.getViewHolder(mockView) }
    }
}