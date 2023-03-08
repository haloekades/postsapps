package com.ekades.movieapps.apps

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.ekades.movieapps.BuildConfig
import com.ekades.movieapps.lib.application.ApplicationProvider
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.stopKoin

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initApplicationProvider()
        initKoin()
    }

    private fun initApplicationProvider() {
        ApplicationProvider.init(this, BuildConfig.DEBUG)
    }

    override fun onTerminate() {
        super.onTerminate()
        if (BuildConfig.DEBUG) {
            stopKoin()
        }
    }

    private fun initKoin() {
        if (isRunningOnTestEnvironment()) {
            stopKoin()
        }
        startKoin(this, AppModules.getModules())
    }

    fun isRunningOnTestEnvironment(): Boolean {
        if (BuildConfig.DEBUG) {
            return try {
                Class.forName("androidx.test.espresso.Espresso")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }

        return false
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}