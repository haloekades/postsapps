package com.ekades.fcmpushnotification.lib.core.test.viewmodels.base

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.*

@Suppress("NOTHING_TO_INLINE")
inline infix fun Any?.shouldBe(value: Any?) {
    assertThat(this, equalTo(value))
}

@Suppress("NOTHING_TO_INLINE")
inline infix fun Any?.shouldNotBe(value: Any?) {
    assertNotEquals(this, value)
}

fun Boolean.shouldBeTrue() {
    assertTrue(this)
}

fun Boolean.shouldBeFalse() {
    assertFalse(this)
}