package com.ekades.poststest.lib.core.ui.extension

val String.capitalizeWords
    get() = this.toLowerCase().split(" ").joinToString(" ") { it.capitalize() }