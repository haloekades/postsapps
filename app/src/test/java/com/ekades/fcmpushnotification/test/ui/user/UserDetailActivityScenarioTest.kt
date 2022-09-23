package com.ekades.fcmpushnotification.test.ui.user

import android.content.Intent
import android.os.Build
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekades.fcmpushnotification.R
import com.ekades.fcmpushnotification.components.AlbumCV
import com.ekades.fcmpushnotification.components.ThumbnailPhotoCV
import com.ekades.fcmpushnotification.lib.core.test.BaseAppTest
import com.ekades.fcmpushnotification.lib.core.test.activities.ActivityScenarioTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBeTrue
import com.ekades.fcmpushnotification.models.*
import com.ekades.fcmpushnotification.ui.user.UserDetailActivity
import com.ekades.fcmpushnotification.ui.user.UserDetailViewModel
import io.mockk.*
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.component_album.*
import kotlinx.android.synthetic.main.component_album.view.*
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class UserDetailActivityScenarioTest : ActivityScenarioTest<UserDetailActivity>() {
    private lateinit var mockViewModel: UserDetailViewModel
    private lateinit var mockUser: User
    private lateinit var mockAlbums: ArrayList<Album>

    override val koinModules: List<Module>
        get() = listOf(
            module(override = true) {
                viewModel { mockViewModel }
            }
        )

    override fun setup() {
        super.setup()

        mockUser = getMockUser()
        mockAlbums = getMockAlbums()

        mockViewModel = spyk()

        mockViewModel.user = mockUser

        every { mockViewModel.getAlbumList() } answers {
            mockViewModel.userAlbums.value = mockAlbums
        }

        scenario = launchActivity(Intent(applicationContext, UserDetailActivity::class.java))
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
                address = spyk(
                    Address(
                        street = fake.aString(),
                        suite = fake.aString(),
                        city = fake.aString(),
                        zipcode = fake.aString()
                    )
                ),
                phone = fake.aString(),
                website = fake.aUrl()
            )
        )
    }

    private fun getMockAlbums(): ArrayList<Album> {
        val albumId = fake.anInt()

        return arrayListOf(
            spyk(
                Album(
                    userId = fake.anInt(),
                    id = albumId,
                    title = fake.aString(),
                    photos = arrayListOf(
                        spyk(
                            Photo(
                                albumId = albumId,
                                id = fake.anInt(),
                                title = fake.aString(),
                                thumbnailUrl = fake.aUrl(),
                                url = fake.aUrl()
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `validate view is displayed is correctly`() {
        onView(withId(R.id.toolbar)).run {
            check(matches(isDisplayed()))
        }
        scenario.onActivity {
            it.toolbar.title shouldBe applicationContext.getString(R.string.title_user_detail)
            it.userNameTextView.text.contains(mockUser.username).shouldBeTrue()
            it.emailTextView.text.contains(mockUser.email).shouldBeTrue()
            it.addressTextView.text.contains(mockUser.address.city).shouldBeTrue()
            it.companyTextView.text.contains(mockUser.company.name).shouldBeTrue()
        }
    }

    @Test
    fun `verify album view is displayed and show photo detail is correctly`() {
        scenario.onActivity {
            val itemView = it.albumRecyclerView.findViewHolderForAdapterPosition(0)?.itemView
            with(itemView as AlbumCV) {
                albumTextView.text shouldBe mockAlbums[0].title

                val thumbnailPhotosView = it.photosRecyclerView.findViewHolderForAdapterPosition(0)?.itemView
                (thumbnailPhotosView as ThumbnailPhotoCV).performClick()

                val activityShadow = Shadows.shadowOf(it)
                val nextActivityClassName = activityShadow?.nextStartedActivity?.component?.className
                nextActivityClassName shouldBe PhotoDetailActivity::class.java.name
            }
        }
    }
}