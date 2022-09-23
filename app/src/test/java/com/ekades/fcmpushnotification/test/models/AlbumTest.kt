package com.ekades.fcmpushnotification.test.models

import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.models.Album
import com.ekades.fcmpushnotification.models.Photo
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Test

class AlbumTest : BaseTest() {
    private val userId = fake.anInt()
    private val id = fake.anInt()
    private val title = fake.aString()
    private var photos = mockk<ArrayList<Photo>>()
    private lateinit var model: Album

    override fun setup() {
        model = spyk(
            Album(
                userId = userId,
                id = id,
                title = title,
                photos = photos
            )
        )
    }

    @Test
    fun `validate model is correctly`() {
        model.userId shouldBe userId
        model.id shouldBe id
        model.title shouldBe title
        model.photos shouldBe photos
    }
}