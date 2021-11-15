package com.ekades.poststest.test.models

import com.ekades.poststest.lib.core.test.viewmodels.base.BaseTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import com.ekades.poststest.models.Address
import io.mockk.spyk
import org.junit.Test

class AddressTest : BaseTest() {
    private val street = fake.aString()
    private val suite = fake.aString()
    private val city = fake.aString()
    private val zipcode = fake.aString()
    private lateinit var model: Address

    override fun setup() {
        model = spyk(
            Address(
                street = street,
                suite = suite,
                city = city,
                zipcode = zipcode
            )
        )
    }

    @Test
    fun `validate model is correctly`() {
        model.street shouldBe street
        model.suite shouldBe suite
        model.city shouldBe city
        model.zipcode shouldBe zipcode
    }
}