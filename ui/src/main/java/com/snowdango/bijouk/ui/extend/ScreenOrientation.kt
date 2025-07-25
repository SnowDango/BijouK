package com.snowdango.bijouk.ui.extend

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun Activity.SetOrientation() {
    val windowSizeClass = calculateWindowSizeClass(this)
    val orientation: Int by remember(windowSizeClass) {
        derivedStateOf {
            if (
                windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
                || windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
            ) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                ActivityInfo.SCREEN_ORIENTATION_FULL_USER
            }
        }
    }
    requestedOrientation = orientation
}