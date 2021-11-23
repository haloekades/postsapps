package com.ekades.poststest.apps

import org.koin.dsl.module.Module
import com.ekades.poststest.di.modules as modulesV2

object AppModules {
    fun getModules(): List<Module> {
        val modules = arrayListOf<Module>()
        modules.addAll(Modules.getModules())
        modules.addAll(modulesV2)
        
        return modules
    }
}