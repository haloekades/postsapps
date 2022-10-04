package com.ekades.temandoa.lib.core.ui.foundation.component

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import com.mikepenz.fastadapter.IClickable
import com.mikepenz.fastadapter.IExpandable
import com.mikepenz.fastadapter.ISubItem
import com.mikepenz.fastadapter.items.AbstractItem
import kotlin.reflect.KClass

class Component<V : View?>(
    internal var type: Int,
    internal var mGenerator: (Context) -> V
) : AbstractItem<Component<V>, ComponentViewHolder<V>>(),
    IExpandable<Component<*>, Component<*>>,
    ISubItem<Component<*>, Component<*>?>,
    IClickable<Component<V>>
{
    @VisibleForTesting
    internal lateinit var mBinder: (V) -> Unit
    @VisibleForTesting
    internal lateinit var mUnbinder: (V) -> Unit
    @VisibleForTesting
    internal var mParent: Component<*>? = null
    @VisibleForTesting
    internal var mSubItems: List<Component<*>> = listOf()
    @VisibleForTesting
    internal var mExpanded = false
    @VisibleForTesting
    internal var mTypedTags: Map<KClass<*>, Any> = mapOf()

    /**
     * The globally unique type of [Component]
     * @return The [type]
     */
    override fun getType(): Int = type

    /**
     * Bind the data into [Component]
     */
    override fun bindView(holder: ComponentViewHolder<V>, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)
        mBinder.invoke(holder.view)
    }

    /**
     * Unbind the data into [Component]
     *
     * @param holder
     */
    override fun unbindView(holder: ComponentViewHolder<V>) {
        super.unbindView(holder)
        mUnbinder.invoke(holder.view)
    }

    /**
     * Generates the [Component]
     * @param context The [Context]
     * @param parent The [ViewGroup]
     * @return The View
     */
    override fun generateView(context: Context, parent: ViewGroup): View = mGenerator.invoke(context) as View

    /**
     * Is the [Component] currently expanded as [Boolean]
     * @return [Boolean] as isExpanded
     */
    override fun isExpanded(): Boolean = mExpanded

    /**
     * Set [Component] with Expandable View. Triggered after NotifyDataSetChanged
     * @param collapsed
     * @return
     */
    override fun withIsExpanded(collapsed: Boolean): Component<V> {
        mExpanded = collapsed
        return this
    }

    /**
     * Set [Component] as Child Items
     * @return [Component]
     */
    override fun withSubItems(subItems: List<Component<*>>): Component<V> {
        mSubItems = subItems
        subItems.forEach {
            it.withParent(this)
        }
        return this
    }

    /**
     * get [Component] Child Items
     * @return [List] of Sub Items as [Component]
     */
    override fun getSubItems(): List<Component<*>>? = mSubItems

    /**
     * Auto expand always returns [Boolean] true.
     * @return
     */
    override fun isAutoExpanding(): Boolean = true

    /**
     * @param parent
     * @return
     */
    override fun withParent(parent: Component<*>?): Component<*> {
        mParent = parent
        return this
    }

    /**
     * if the [Component] is a child, it will return the parent [Component]
     * @return [Component] as Parent
     */
    override fun getParent(): Component<*>? = mParent

    /**
     * @param binder as [Unit]
     * @return called when visible in [RecyclerView]
     */
    fun onAttached(binder: (V) -> Unit): Component<V> {
        this.mBinder = binder
        return this
    }

    /**
     * @param unbinder as [Unit]
     * @return called when not visible in [RecyclerView]
     */
    fun onDetached(unbinder: (V) -> Unit): Component<V> {
        this.mUnbinder = unbinder
        return this
    }

    /**
     * Set the identifier of this [Component]
     *
     * @param identifier
     * @return
     */
    fun setIdentifier(identifier: String): Component<V> {
        mIdentifier = identifier.hashCode().toLong()
        return this
    }

    /**
     * Set tag to [Component], can contain multiple tags and can be any type
     * @return [Component]
     */
    fun <T : Any> setTag(key: KClass<T>, value: T): Component<V> {
        mTypedTags = mTypedTags.plus(key to value)
        return this
    }

    /**
     * Get [Component] Tag
     * @return [T]
     */
    fun <T : Any> getTag(key: KClass<T>): T? {
        return mTypedTags[key]?.let { it as T }
    }

    @Deprecated("Don't use this, only overriding method")
    override fun getLayoutRes(): Int {
        return 0
    }

    @Deprecated("Don't use this, only overriding method")
    override fun getViewHolder(parent: ViewGroup): ComponentViewHolder<V> {
        return getViewHolder(generateView(parent.context, parent))
    }

    @Deprecated("Don't use this, only overriding method")
    override fun getViewHolder(v: View): ComponentViewHolder<V> {
        return ComponentViewHolder(v)
    }
}