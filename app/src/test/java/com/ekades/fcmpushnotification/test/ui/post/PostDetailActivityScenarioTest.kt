package com.ekades.fcmpushnotification.test.ui.post

import android.content.Intent
import android.os.Build
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekades.fcmpushnotification.R
import com.ekades.fcmpushnotification.components.CommentCV
import com.ekades.fcmpushnotification.lib.core.test.BaseAppTest
import com.ekades.fcmpushnotification.lib.core.test.activities.ActivityScenarioTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.models.Comment
import com.ekades.fcmpushnotification.models.Company
import com.ekades.fcmpushnotification.models.Post
import com.ekades.fcmpushnotification.models.User
import com.ekades.fcmpushnotification.ui.post.PostDetailActivity
import com.ekades.fcmpushnotification.ui.post.PostDetailViewModel
import com.ekades.fcmpushnotification.ui.user.UserDetailActivity
import io.mockk.*
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.component_comment.view.*
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class PostDetailActivityScenarioTest : ActivityScenarioTest<PostDetailActivity>() {
    private lateinit var mockViewModel: PostDetailViewModel
    private lateinit var mockUser: User
    private lateinit var mockPost: Post
    private lateinit var mockComments: ArrayList<Comment>

    override val koinModules: List<Module>
        get() = listOf(
            module(override = true) {
                viewModel { mockViewModel }
            }
        )

    override fun setup() {
        super.setup()

        mockUser = getMockUser()
        mockPost = getMockPost()
        mockComments = getMockComments()

        mockViewModel = spyk()
        mockViewModel.user = mockUser
        mockViewModel.post = mockPost

        every { mockViewModel.getCommentList() } answers {
            mockViewModel.comments.value = mockComments
        }

        scenario = launchActivity(Intent(applicationContext, PostDetailActivity::class.java))
    }

    private fun getMockUser(): User {
        return spyk(
            User(
                id = fake.anInt(),
                name = fake.aString(),
                username = fake.aString(),
                email = fake.aString(),
                company = spyk(
                    Company(
                        name = fake.aString(),
                        catchPhrase = fake.aString(),
                        bs = fake.aString()
                    )
                ),
                address = mockk(),
                phone = fake.aString(),
                website = fake.aUrl()
            )
        )
    }

    private fun getMockPost(): Post {
        return spyk(
            Post(
                userId = fake.anInt(),
                id = fake.anInt(),
                title = fake.aString(),
                body = fake.aString()
            )
        )
    }

    private fun getMockComments(): ArrayList<Comment> {
        return arrayListOf(
            spyk(
                Comment(
                    postId = fake.anInt(),
                    id = fake.anInt(),
                    name = fake.aString(),
                    body = fake.aString(),
                    email = fake.anEmail()
                )
            )
        )
    }

    @Test
    fun `validate view is displayed and open user page is correctly`() {
        onView(withId(R.id.toolbar)).run {
            check(matches(isDisplayed()))
        }
        scenario.onActivity {
            it.toolbar.title shouldBe applicationContext.getString(R.string.title_post_detail)
            it.userNameTextView.text shouldBe mockUser.username
            it.userCompanyNameTextView.text shouldBe mockUser.company.name
            it.titleTextView.text shouldBe mockPost.title

            it.userNameTextView.performClick()

            val activityShadow = Shadows.shadowOf(it)
            val nextActivityClassName = activityShadow?.nextStartedActivity?.component?.className

            nextActivityClassName shouldBe UserDetailActivity::class.java.name
        }
    }

    @Test
    fun `verify comments view is displayed correctly`() {
        scenario.onActivity {
            val itemView = it.commentRecyclerView.findViewHolderForAdapterPosition(0)?.itemView
            with(itemView as CommentCV) {
                authorNameTextView.text shouldBe mockComments[0].name
            }
        }
    }
}