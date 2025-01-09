package com.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.interop.LocalUIViewController
import com.util.ui.OrientationManager
import com.util.ui.hideStatusBar
import platform.UIKit.UIInterfaceOrientationMaskAll
import platform.UIKit.UIInterfaceOrientationMaskPortrait
import platform.UIKit.setNeedsUpdateOfSupportedInterfaceOrientations


@Composable
fun SetOrientationPortrait() {
    val localViewController = LocalUIViewController.current
    DisposableEffect(Unit) {
        hideStatusBar()
        val manager = OrientationManager()
        manager.setOrientation(UIInterfaceOrientationMaskPortrait)
        localViewController.setNeedsUpdateOfSupportedInterfaceOrientations()
        onDispose {
        }
    }
}

@Composable
fun SetOrientationFullSensor() {
    val localViewController = LocalUIViewController.current
    DisposableEffect(Unit) {
        val manager = OrientationManager()
        manager.setOrientation(UIInterfaceOrientationMaskAll)
        localViewController.setNeedsUpdateOfSupportedInterfaceOrientations()
        onDispose {
        }
    }
}

