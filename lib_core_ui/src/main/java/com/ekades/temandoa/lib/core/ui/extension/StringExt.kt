package com.ekades.temandoa.lib.core.ui.extension

val String.capitalizeWords
    get() = this.toLowerCase().split(" ").joinToString(" ") { it.capitalize() }