package com.util.ui

import platform.UIKit.UIApplication
import platform.UIKit.setStatusBarHidden

fun hideStatusBar() {
    UIApplication.sharedApplication.setStatusBarHidden(hidden = true, animated = true)
}