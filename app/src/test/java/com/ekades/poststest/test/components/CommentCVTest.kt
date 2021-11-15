package com.ekades.poststest.test.components

import android.app.Activity
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekades.poststest.components.CommentCV
import com.ekades.poststest.lib.application.ApplicationProvider
import com.ekades.poststest.lib.core.test.BaseAppTest
import com.ekades.poststest.lib.core.test.viewmodels.base.BaseTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import com.ekades.poststest.models.Comment
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.spyk
import kotlinx.android.synthetic.main.component_comment.view.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import androidx.test.core.app.ApplicationProvider as TestApp

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class CommentCVTest : BaseTest() {

    private lateinit var activityController: ActivityController<Activity>
    private lateinit var activity: Activity
    private lateinit var commentCV: CommentCV

    override fun setup() {
        super.setup()
        mockkObject(ApplicationProvider)
        every { ApplicationProvider.context } returns TestApp.getApplicationContext()

        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()
        commentCV = CommentCV(activity)
    }

    override fun tearAndDown() {
        super.tearAndDown()
        activityController.get().finish()
    }

    @Test
    fun `assert default state should return correct value`() {
        val mockComment = spyk(
            Comment(
                postId = fake.anInt(),
                id = fake.anInt(),
                body = fake.aString(),
                name = fake.aString(),
                email = fake.anEmail()
            )
        )

        commentCV.bind {
            comment = mockComment
        }

        commentCV.bodyTextView.text shouldBe mockComment.body
        commentCV.authorNameTextView.text shouldBe mockComment.name
    }
}