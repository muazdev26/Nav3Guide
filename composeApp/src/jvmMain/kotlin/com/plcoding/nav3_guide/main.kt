package com.plcoding.nav3_guide

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Nav3Guide",
    ) {
        App()
    }
}