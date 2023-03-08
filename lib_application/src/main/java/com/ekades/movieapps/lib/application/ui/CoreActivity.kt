package com.ekades.movieapps.lib.application.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.viewmodel.ext.android.viewModelByClass
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

abstract class CoreActivity<V: ViewModel>(clazz: KClass<V>) : AppCompatActivity(), CoroutineScope {
    val mJob: Job by lazy { Job() }
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main
    
    var activityLayoutRes: Int? = null
    val viewModel: V by viewModelByClass(clazz)

    abstract fun render(): Job
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLayoutRes?.let { setContentView(it) }
        render()
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}