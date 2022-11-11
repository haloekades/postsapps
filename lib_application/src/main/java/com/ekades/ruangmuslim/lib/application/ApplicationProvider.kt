package com.ekades.ruangmuslim.lib.application

import android.content.Context

object ApplicationProvider {
    /**
     * Application Context [Context]
     */
    lateinit var context: Context
        private set
    
    /**
     * Determine if App is in Debug Mode [Boolean]
     */
    var isDebugMode: Boolean = true
        private set

    val isSandBoxMode get() = isSandBoxVersionName || isDebugMode

    val isSandBoxVersionName get() = ".*[A-Za-z]".toRegex().containsMatchIn(BuildConfig.VERSION_NAME)

    /**
     * Init Singleton on App Startup
     */
    fun init(context: Context, isDebugMode: Boolean) {
        this.context = context
        this.isDebugMode = isDebugMode
    }

    /**
     * Force Crash on Debug Mode Only!
     */
    fun forceCrash(message: String?) {
        if (isDebugMode) {
            throw RuntimeException(message)
        }
    }

    fun initTesting(context: Context, isDebugMode: Boolean) {
        this.context = context
        this.isDebugMode = isDebugMode
    }
}