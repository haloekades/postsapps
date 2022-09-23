package com.ekades.fcmpushnotification.lib.core.test

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.stopKoin

class BaseAppTest : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, emptyList())
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}