package com.ekades.fcmpushnotification.test.models

import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.models.Post
import io.mockk.spyk
import org.junit.Test

class PostTest : BaseTest() {
    private val userId = fake.anInt()
    private val id = fake.anInt()
    private val title = fake.aString()
    private val body = fake.aString()
    private val userName = fake.aString()
    private val userCompanyName = fake.aString()

    private lateinit var model: Post

    override fun setup() {
        model = spyk(
            Post(
                userId = userId,
                id = id,
                title = title,
                body = body,
                userName = userName,
                userCompanyName = userCompanyName
            )
        )
    }

    @Test
    fun `validate model is correctly`() {
        model.userId shouldBe userId
        model.id shouldBe id
        model.title shouldBe title
        model.body shouldBe body
        model.userName shouldBe userName
        model.userCompanyName shouldBe userCompanyName
    }
}