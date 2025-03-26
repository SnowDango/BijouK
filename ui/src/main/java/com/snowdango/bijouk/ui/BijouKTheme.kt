package com.snowdango.bijouk.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.materialkolor.rememberDynamicColorScheme

@Composable
fun BijouKTheme(
    seedColor: Color = Color("#4B43DF".toColorInt()),
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = rememberDynamicColorScheme(seedColor = seedColor, isDark = darkTheme, isAmoled = false)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
