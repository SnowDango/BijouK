package com.snowdango.bijouk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.snowdango.bijouk.features.device.DeviceScreen
import com.snowdango.bijouk.features.now_play.NowPlayScreen
import com.snowdango.bijouk.ui.BijouKTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            BijouKTheme {
                NavHost(
                    navController = navController,
                    startDestination = Route.DEVICE,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    composable<Route.DEVICE> {
                        DeviceScreen(
                            onClickDevice = { data ->
                                navController.navigate(
                                    Route.NOW_PLAY(
                                        id = data.id,
                                        name = data.name,
                                        baseUrl = data.baseUrl,
                                        token = data.token
                                    )
                                )
                            }
                        )
                    }
                    composable<Route.NOW_PLAY> { backStackEntry ->
                        val nowPlay = backStackEntry.toRoute<Route.NOW_PLAY>()
                        NowPlayScreen(nowPlay.id, nowPlay.name, nowPlay.baseUrl, nowPlay.token)
                    }
                }
            }
        }
    }

    sealed class Route {
        @Serializable
        object DEVICE : Route()

        @Serializable
        data class NOW_PLAY(
            val id: Long,
            val name: String,
            val baseUrl: String,
            val token: String
        ) : Route()
    }
}
