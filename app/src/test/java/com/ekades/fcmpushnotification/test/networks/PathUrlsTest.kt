package com.ekades.fcmpushnotification.test.networks

import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.networks.PathUrls
import org.junit.Test

class PathUrlsTest : BaseTest() {

    @Test
    fun `verify PathUrls value is correctly`() {
        with(PathUrls) {
            POSTS shouldBe "posts"
            USERS shouldBe "users"
            COMMENTS shouldBe "comments"
            ALBUMS shouldBe "albums"
            PHOTOS shouldBe "photos"
        }
    }
}