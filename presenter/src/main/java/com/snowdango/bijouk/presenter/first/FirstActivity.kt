package com.snowdango.bijouk.presenter.first

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.snowdango.bijouk.presenter.first.content.FirstScreen
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.extend.SetOrientation

class FirstActivity : ComponentActivity() {

    private val requestPermission = registerForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        callback = {},
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
        requestPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}
