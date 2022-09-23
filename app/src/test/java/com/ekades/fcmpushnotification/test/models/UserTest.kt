package com.ekades.fcmpushnotification.test.models

import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.models.*
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Test

class UserTest : BaseTest() {
    private val id = fake.anInt()
    private val name = fake.aString()
    private val username = fake.aString()
    private val email = fake.aString()
    private val address = mockk<Address>()
    private val phone = fake.aString()
    private val website = fake.aString()
    private val company = mockk<Company>()
    private lateinit var model: User

    override fun setup() {
        model = spyk(
            User(
                id = id,
                name = name,
                username = username,
                email = email,
                address = address,
                phone = phone,
                website = website,
                company = company
            )
        )
    }

    @Test
    fun `validate model is correctly`() {
        model.id shouldBe id
        model.name shouldBe name
        model.username shouldBe username
        model.email shouldBe email
        model.address shouldBe address
        model.phone shouldBe phone
        model.website shouldBe website
        model.company shouldBe company
    }
}