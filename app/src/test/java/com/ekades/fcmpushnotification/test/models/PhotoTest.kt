package com.ekades.fcmpushnotification.test.models

import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.models.Photo
import io.mockk.spyk
import org.junit.Test

class PhotoTest : BaseTest() {
    private val albumId = fake.anInt()
    private val id = fake.anInt()
    private val title = fake.aString()
    private val url = fake.aString()
    private val thumbnailUrl = fake.aString()
    private lateinit var model: Photo

    override fun setup() {
        model = spyk(
            Photo(
                albumId = albumId,
                id = id,
                title = title,
                url = url,
                thumbnailUrl = thumbnailUrl
            )
        )
    }

    @Test
    fun `validate model is correctly`() {
        model.albumId shouldBe albumId
        model.id shouldBe id
        model.title shouldBe title
        model.url shouldBe url
        model.thumbnailUrl shouldBe thumbnailUrl
    }
}