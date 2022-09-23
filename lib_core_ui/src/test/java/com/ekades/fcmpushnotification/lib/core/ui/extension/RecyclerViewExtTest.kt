package com.ekades.fcmpushnotification.lib.core.ui.extension

import android.app.Activity
import android.os.Build
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekades.fcmpushnotification.lib.application.ApplicationProvider
import com.ekades.fcmpushnotification.lib.core.test.BaseAppTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseTest
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class RecyclerViewExtTest : BaseTest() {
    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity

    private lateinit var recyclerView: RecyclerView

    override fun setup() {
        super.setup()
        mockkStatic("com.ekades.poststest.lib.core.ui.extension.RecyclerViewExtKt")

        mockkObject(ApplicationProvider)
        every { ApplicationProvider.context } returns androidx.test.core.app.ApplicationProvider.getApplicationContext()

        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()

        recyclerView = RecyclerView(activity)
    }

    @Test
    fun `recyclerview linearLayoutAdapter returns correct layout manager`() = runBlocking {
        recyclerView.linearLayoutAdapter(activity, VERTICAL)

        assertThat(recyclerView.layoutManager, instanceOf<Any>(LinearLayoutManager::class.java))
    }

    @Test
    fun `recyclerview gridLayoutAdapter returns correct layout manager`() = runBlocking {
        recyclerView.gridLayoutAdapter(activity, 2)

        assertThat(recyclerView.layoutManager, instanceOf<Any>(GridLayoutManager::class.java))
    }

    @Test
    fun `recyclerview staggeredGridLayout returns correct layout manager`() = runBlocking {
        recyclerView.staggeredGridLayoutAdapter(2, VERTICAL)

        assertThat(recyclerView.layoutManager, instanceOf<Any>(StaggeredGridLayoutManager::class.java))
    }
}