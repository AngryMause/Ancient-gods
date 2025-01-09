package com

import androidx.compose.ui.window.ComposeUIViewController
import com.koindi.initKoin
import com.ui.presentation.navigation.AppNavHost

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    AppNavHost()
}
