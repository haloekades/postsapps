package com.ekades.movieapps.apps

import org.koin.dsl.module.Module
import com.ekades.movieapps.di.modules as modulesV2

object AppModules {
    fun getModules(): List<Module> {
        val modules = arrayListOf<Module>()
        modules.addAll(modulesV2)
        
        return modules
    }
}