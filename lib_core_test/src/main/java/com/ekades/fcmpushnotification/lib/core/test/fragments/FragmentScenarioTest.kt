package com.ekades.fcmpushnotification.lib.core.test.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ApplicationProvider
import fr.xgouchet.elmyr.junit.JUnitForger
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.dsl.module.Module
import org.koin.standalone.StandAloneContext

abstract class FragmentScenarioTest<F: Fragment> {
    @get:Rule
    val fake = JUnitForger()

    lateinit var applicationContext: Context
    lateinit var scenario: FragmentScenario<F>
    
    open val koinModules: List<Module> = emptyList()

    @Before
    open fun setup() {
        applicationContext = ApplicationProvider.getApplicationContext()
        StandAloneContext.loadKoinModules(koinModules)
    }

    @After
    open fun cleanup() {
        StandAloneContext.stopKoin()
    }
}