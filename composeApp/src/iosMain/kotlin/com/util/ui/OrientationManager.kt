package com.util.ui

import org.jetbrains.skiko.OS
import org.jetbrains.skiko.OSVersion
import org.jetbrains.skiko.available
import platform.Foundation.setValue
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIInterfaceOrientationMask
import platform.UIKit.UIInterfaceOrientationMaskAll
import platform.UIKit.UIInterfaceOrientationMaskAllButUpsideDown
import platform.UIKit.UIInterfaceOrientationMaskLandscape
import platform.UIKit.UIInterfaceOrientationMaskLandscapeLeft
import platform.UIKit.UIInterfaceOrientationMaskLandscapeRight
import platform.UIKit.UIInterfaceOrientationMaskPortrait
import platform.UIKit.UIInterfaceOrientationMaskPortraitUpsideDown
import platform.UIKit.UIViewController
import platform.UIKit.UIWindowScene
import platform.UIKit.UIWindowSceneGeometryPreferencesIOS
import platform.UIKit.attemptRotationToDeviceOrientation

class   OrientationManager() {
    companion object {
        var orientation: Int = 0
    }

    fun setOrientation(newOrientation: UIInterfaceOrientationMask = UIInterfaceOrientationMaskPortrait) {
        if (available(OS.Ios to OSVersion(16))) {
            val windowScene =
                UIApplication.sharedApplication().connectedScenes.first() as? UIWindowScene
            windowScene?.requestGeometryUpdateWithPreferences(
                UIWindowSceneGeometryPreferencesIOS(interfaceOrientations = newOrientation)
            ) {}
        } else {
            UIDevice.currentDevice().setValue(newOrientation, forKey = "orientation")
            UIViewController.attemptRotationToDeviceOrientation()
        }
        orientation = newOrientation.getOrientationInt()
    }

    fun unlockOrientation(defaultOrientation: UIInterfaceOrientationMask = UIInterfaceOrientationMaskAll) {
        if (available(OS.Ios to OSVersion(16))) {
            val windowScene =
                UIApplication.sharedApplication().connectedScenes.first() as? UIWindowScene
            windowScene?.requestGeometryUpdateWithPreferences(
                UIWindowSceneGeometryPreferencesIOS(interfaceOrientations = defaultOrientation)
            ) {}
        } else {
            UIDevice.currentDevice().setValue(defaultOrientation, forKey = "orientation")
            UIViewController.attemptRotationToDeviceOrientation()
        }
        orientation = defaultOrientation.getOrientationInt()
    }

    private fun UIInterfaceOrientationMask.getOrientationInt(): Int {
        return when (this) {
            UIInterfaceOrientationMaskAll -> 0
            UIInterfaceOrientationMaskAllButUpsideDown -> 1
            UIInterfaceOrientationMaskPortrait -> 2
            UIInterfaceOrientationMaskPortraitUpsideDown -> 3
            UIInterfaceOrientationMaskLandscape -> 4
            UIInterfaceOrientationMaskLandscapeLeft -> 5
            UIInterfaceOrientationMaskLandscapeRight -> 6
            else -> 0
        }
    }
}
