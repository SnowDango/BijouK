package com.snowdango.bijouk.presenter.first

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.snowdango.bijouk.presenter.first.content.FirstScreen
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.extend.SetOrientation

class FirstActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            SetOrientation()
            BijouKTheme {
                FirstScreen()
            }
        }
    }
}
