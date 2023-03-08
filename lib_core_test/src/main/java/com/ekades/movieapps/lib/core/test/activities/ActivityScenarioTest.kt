package com.ekades.movieapps.lib.core.test.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import com.ekades.movieapps.lib.application.ApplicationProvider
import androidx.test.core.app.ApplicationProvider as TestApp
import fr.xgouchet.elmyr.junit.JUnitForger
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.dsl.module.Module
import org.koin.standalone.StandAloneContext
import org.koin.standalone.StandAloneContext.loadKoinModules

abstract class ActivityScenarioTest<A: AppCompatActivity> {
    @get:Rule
    val fake = JUnitForger()
    
    open val koinModules: List<Module> = emptyList()

    lateinit var applicationContext: Context
    lateinit var scenario: ActivityScenario<A>

    @Before
    open fun setup() {
        mockkObject(ApplicationProvider)
        every { ApplicationProvider.context } returns TestApp.getApplicationContext()
        applicationContext = TestApp.getApplicationContext()
        loadKoinModules(koinModules)
    }

    @After
    open fun cleanup() {
        unmockkAll()
        scenario.close()
        StandAloneContext.stopKoin()
    }
}