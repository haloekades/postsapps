package com.ekades.fcmpushnotification.test.networks.dataSource

import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseDataSourceTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.networks.dataSource.MainDataSource
import io.mockk.every
import io.mockk.spyk
import org.junit.Test

class MainDataSourceTest : BaseDataSourceTest<MainDataSource>() {

    override fun setup() {
        super.setup()
        dataSource = spyk()
        every { dataSource.execute(mockLiveData) } returns mockDisposable
    }

    @Test
    fun `verify getPosts when injected`() {
        with(dataSource) {
            val result = getPosts(mockLiveData)

            result shouldBe execute(mockLiveData)

            path shouldBe PathUrls.POSTS
        }
    }

    @Test
    fun `verify getUsers when injected`() {
        with(dataSource) {
            val result = getUsers(mockLiveData)

            result shouldBe execute(mockLiveData)

            path shouldBe PathUrls.USERS
        }
    }

    @Test
    fun `verify getComments when injected`() {
        with(dataSource) {
            val postId = fake.anInt()
            val result = getComments(mockLiveData, postId)

            result shouldBe execute(mockLiveData)

            path shouldBe "${PathUrls.COMMENTS}?${MainDataSource.PARAM_POST_ID}=$postId"
        }
    }

    @Test
    fun `verify getAlbums when injected`() {
        with(dataSource) {
            val userId = fake.anInt()
            val result = getAlbums(mockLiveData, userId)

            result shouldBe execute(mockLiveData)

            path shouldBe "${PathUrls.ALBUMS}?${MainDataSource.PARAM_USER_ID}=$userId"
        }
    }

    @Test
    fun `verify getPhotos when injected`() {
        with(dataSource) {
            val albumId = fake.anInt()
            val result = getPhotos(mockLiveData, albumId)

            result shouldBe execute(mockLiveData)

            path shouldBe "${PathUrls.PHOTOS}?${MainDataSource.PARAM_ALBUM_ID}=$albumId"
        }
    }
}