package com.snowdango.bijouk.presenter.first

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.snowdango.bijouk.presenter.first.content.FirstScreen
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.InitScreenOrientation

class FirstActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            InitScreenOrientation()
            BijouKTheme {
                FirstScreen()
            }
        }
    }
}
