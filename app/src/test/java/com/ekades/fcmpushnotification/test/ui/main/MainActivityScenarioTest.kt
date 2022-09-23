package com.ekades.fcmpushnotification.test.ui.main

import android.content.Intent
import android.os.Build
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekades.fcmpushnotification.R
import com.ekades.fcmpushnotification.components.MainPostCV
import com.ekades.fcmpushnotification.ui.main.MainActivity
import com.ekades.fcmpushnotification.ui.main.MainViewModel
import com.ekades.fcmpushnotification.lib.core.test.BaseAppTest
import com.ekades.fcmpushnotification.lib.core.test.activities.ActivityScenarioTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.models.Post
import com.ekades.fcmpushnotification.ui.post.PostDetailActivity
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.spyk
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.component_main_post.view.*
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class MainActivityScenarioTest : ActivityScenarioTest<MainActivity>() {
    private lateinit var mockViewModel: MainViewModel

    override val koinModules: List<Module>
        get() = listOf(
            module(override = true) {
                viewModel { mockViewModel }
            }
        )

    override fun setup() {
        super.setup()
        mockViewModel = spyk()

        every { mockViewModel.getPostList() } just runs
        every { mockViewModel.getUserList() } just runs

        scenario = launchActivity(Intent(applicationContext, MainActivity::class.java))
    }

    @Test
    fun `validate view is displayed correctly`() {
        onView(withId(R.id.toolbar)).run {
            check(matches(isDisplayed()))
        }
        scenario.onActivity {
            it.toolbar.title shouldBe applicationContext.getString(R.string.title_app_name)
        }
    }

    @Test
    fun `verify shown list & open post detail when succesfully load posts`() {
        val mockTitle = fake.aString()
        mockViewModel.mediatorPostUsers.value = spyk(
            arrayListOf(
                Post(
                    userId = fake.anInt(),
                    id = fake.anInt(),
                    title = mockTitle,
                    body = fake.aString(),
                    userName = fake.aString(),
                    userCompanyName = fake.aString()
                )
            )
        )

        scenario.onActivity {
            val itemView = it.postsRecyclerView.findViewHolderForAdapterPosition(0)?.itemView
            with(itemView as MainPostCV) {
                titleTextView.text shouldBe mockTitle
                performClick()
            }

            val activityShadow = Shadows.shadowOf(it)
            val nextActivityClassName = activityShadow?.nextStartedActivity?.component?.className

            nextActivityClassName shouldBe PostDetailActivity::class.java.name
        }
    }
}