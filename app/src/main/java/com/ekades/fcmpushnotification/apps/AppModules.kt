package com.ekades.fcmpushnotification.apps

import org.koin.dsl.module.Module

object AppModules {
    fun getModules(): List<Module> {
        val modules = arrayListOf<Module>()
        modules.addAll(Modules.getModules())
        
        return modules
    }
}