package com.snowdango.bijouk.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun Activity.InitScreenOrientation() {
    requestedOrientation = if (isCompact()) {
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    } else {
        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}

@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
private fun Activity.isCompact(): Boolean {
    val windowSizeClass = calculateWindowSizeClass(this)
    return windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
        windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
}
