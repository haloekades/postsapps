package com.ekades.poststest.features.splash

import com.ekades.poststest.R
import com.ekades.poststest.features.main.MainActivity
import com.ekades.poststest.lib.application.ui.CoreActivity
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.lib.ui.asset.attributes.IconSize
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*

class SplashActivity : CoreActivity<BaseViewModel>(BaseViewModel::class) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var spalshJob : Job? = null

    init {
        activityLayoutRes = R.layout.activity_splash
    }

    override fun render() = launch(Dispatchers.Main) {
        setupIcon()
        runJob()
    }

    private fun runJob() {
        spalshJob = coroutineScope.launch {
            delay(DELAY)
            openMainActivity()
        }
    }

    private fun setupIcon(){
        iconCV.bind {
            imageSize = IconSize.XXXLARGE
            imageDrawable = R.drawable.ic_teman_doa
        }
    }

    private fun openMainActivity() {
        startActivity(MainActivity.newIntent(this))
        finishAffinity()
    }

    companion object {
        private const val DELAY: Long = 2500
    }
}