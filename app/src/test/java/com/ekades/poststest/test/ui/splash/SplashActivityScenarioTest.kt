package com.ekades.poststest.test.ui.splash

import android.content.Intent
import android.os.Build
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekades.poststest.R
import com.ekades.poststest.lib.core.test.BaseAppTest
import com.ekades.poststest.lib.core.test.activities.ActivityScenarioTest
import com.ekades.poststest.lib.core.test.viewmodels.base.shouldBe
import com.ekades.poststest.ui.main.MainActivity
import com.ekades.poststest.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class, manifest = Config.NONE)
class SplashActivityScenarioTest : ActivityScenarioTest<SplashActivity>() {

    override fun setup() {
        super.setup()
        scenario = launchActivity(Intent(applicationContext, SplashActivity::class.java))
    }

    @Test
    fun `validate view is displayed and goto main after two second correctly`() {
        val coroutineScope = CoroutineScope(Dispatchers.Main)

        scenario.onActivity {
            it.appNameTextView.text shouldBe applicationContext.getString(R.string.app_name)
        }

        coroutineScope.launch {
            delay(SplashActivity.SPLASH_DELAY)
            scenario.onActivity {
                val activityShadow = Shadows.shadowOf(it)
                val nextActivityClassName =
                    activityShadow?.nextStartedActivity?.component?.className

                nextActivityClassName shouldBe MainActivity::class.java.name
            }
        }
    }
}