package com.ekades.poststest.test.models

import com.ekades.poststest.lib.core.test.viewmodels.base.BaseTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import com.ekades.poststest.models.Comment
import io.mockk.spyk
import org.junit.Test

class CommentTest : BaseTest() {
    private val postId = fake.anInt()
    private val id = fake.anInt()
    private val name = fake.aString()
    private val email = fake.aString()
    private val body = fake.aString()
    private lateinit var model: Comment

    override fun setup() {
        model = spyk(
            Comment(
                postId = postId,
                id = id,
                name = name,
                email = email,
                body = body
            )
        )
    }

    @Test
    fun `validate model is correctly`() {
        model.postId shouldBe postId
        model.id shouldBe id
        model.name shouldBe name
        model.email shouldBe email
        model.body shouldBe body
    }
}