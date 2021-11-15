package com.ekades.poststest.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.ekades.poststest.R
import com.ekades.poststest.ui.main.MainActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var splashJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        runSplash()
    }

    private fun runSplash() {
        splashJob = coroutineScope.launch {
            delay(SPLASH_DELAY)
            gotoMain()
        }
    }

    private fun gotoMain() {
        startActivity(MainActivity.newIntent(this))
        finishAffinity()
    }

    companion object {
        @VisibleForTesting
        const val SPLASH_DELAY = 2000L
    }

}