package com.snowdango.bijouk.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.materialkolor.rememberDynamicColorScheme

@Composable
fun BijouKTheme(
    seedColor: Color = colorResource(R.color.seed_color),
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        rememberDynamicColorScheme(seedColor = seedColor, isDark = darkTheme, isAmoled = false)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
