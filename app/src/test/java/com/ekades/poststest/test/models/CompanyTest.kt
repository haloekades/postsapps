package com.ekades.poststest.test.models

import com.ekades.poststest.lib.core.test.viewmodels.base.BaseTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import com.ekades.poststest.models.Company
import io.mockk.spyk
import org.junit.Test

class CompanyTest : BaseTest() {
    private val name = fake.aString()
    private val catchPhrase = fake.aString()
    private val bs = fake.aString()
    private lateinit var model: Company

    override fun setup() {
        model = spyk(
            Company(
                name = name,
                catchPhrase = catchPhrase,
                bs = bs
            )
        )
    }

    @Test
    fun `validate model is correctly`() {
        model.name shouldBe name
        model.catchPhrase shouldBe catchPhrase
        model.bs shouldBe bs
    }
}