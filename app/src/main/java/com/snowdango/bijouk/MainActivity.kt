package com.snowdango.bijouk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withContext
import com.snowdango.bijouk.features.device.DeviceScreen
import com.snowdango.bijouk.features.nowPlay.NowPlayScreen
import com.snowdango.bijouk.setting.OSSLicenseScreen
import com.snowdango.bijouk.setting.SettingScreen
import com.snowdango.bijouk.ui.BijouKTheme
import kotlinx.serialization.Serializable
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    val libs by inject<Libs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            BijouKTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.DEVICE,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
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
                            NowPlayScreen(nowPlay.name, nowPlay.baseUrl, nowPlay.token)
                        }
                        composable<Route.SETTING> {
                            SettingScreen(
                                onClickLicense = {
                                    navController.navigate(Route.OSS_LICENSE)
                                }
                            )
                        }
                        composable<Route.OSS_LICENSE> {
                            OSSLicenseScreen(
                                libs = Libs.Builder().withContext(LocalContext.current).build()
                            )
                        }
                    }
                    val currentRouteState = navController.currentBackStackEntryAsState()
                    val route = Route.fromNavBackStackEntry(currentRouteState.value)
                    if (bottomRoutes.any { it.route == route }) {
                        NavigationBar {
                            bottomRoutes.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icon, contentDescription = null) },
                                    label = { Text(item.name) },
                                    selected = route == item.route,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    val bottomRoutes = listOf<BottomRoute>(
        BottomRoute(Route.DEVICE, Icons.Default.Computer, "Devices"),
        BottomRoute(Route.SETTING, Icons.Default.Settings, "Settings"),
    )

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

        @Serializable
        object SETTING : Route()

        @Serializable
        object OSS_LICENSE : Route()

        companion object {
            @Composable
            fun fromNavBackStackEntry(navBackStackEntry: NavBackStackEntry?): Route? {
                if (navBackStackEntry?.destination?.route == null) return null
                return when (navBackStackEntry.destination.route!!) {
                    in DEVICE.serializer().descriptor.serialName -> {
                        navBackStackEntry.toRoute<DEVICE>()
                    }

                    in NOW_PLAY.serializer().descriptor.serialName -> {
                        navBackStackEntry.toRoute<NOW_PLAY>()
                    }

                    in SETTING.serializer().descriptor.serialName -> {
                        navBackStackEntry.toRoute<SETTING>()
                    }

                    in OSS_LICENSE.serializer().descriptor.serialName -> {
                        navBackStackEntry.toRoute<OSS_LICENSE>()
                    }

                    else -> null
                }
            }
        }
    }

    data class BottomRoute(
        val route: Route,
        val icon: ImageVector,
        val name: String,
    )
}
