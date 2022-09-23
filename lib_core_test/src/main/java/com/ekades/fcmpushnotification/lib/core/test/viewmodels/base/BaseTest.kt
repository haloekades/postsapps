package com.ekades.fcmpushnotification.lib.core.test.viewmodels.base

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import fr.xgouchet.elmyr.junit.JUnitForger
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.standalone.StandAloneContext.stopKoin

abstract class BaseTest {
    @get:Rule
    val fake = JUnitForger()

    lateinit var mockContext: Context
    lateinit var mockActivity: Activity
    lateinit var mockFragment: Fragment
    var sharedPreferences: SharedPreferences = mockk(relaxed = true)

    @Before
    open fun setup() {
        mockContext = mockk(relaxed = true)
        mockActivity = mockk(relaxed = true)
        mockFragment = mockk(relaxed = true)
    }

    @After
    open fun tearAndDown() {
        unmockkAll()
        stopKoin()
    }
}