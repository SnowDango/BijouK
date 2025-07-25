package com.snowdango.bijouk.ui.extend

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

enum class ScreenType {
    SINGLE,
    SEPARATED,
}

@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun Activity.screenType(): ScreenType {
    val windowSizeClass = calculateWindowSizeClass(this)
    val screenType: ScreenType by remember(windowSizeClass) {
        derivedStateOf {
            if (
                windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
            ) {
                ScreenType.SINGLE
            } else if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium) {
                ScreenType.SINGLE
            } else {
                ScreenType.SEPARATED
            }
        }
    }
    return screenType
}

/*
 Smart Phone ->
    Portrait: Width: Compact, Height: Medium
    Landscape: Width: Medium, Height: Compact
 Foldable Phone ->
    Portrait: Width: Expanded, Height: Medium
    Landscape: Width: Medium, Height: Medium
  Tablet ->
    Portrait: Width: Medium, Height: Expanded
    Landscape: Width: Expanded,  Height: Medium
*/
