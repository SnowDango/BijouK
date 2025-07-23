package com.snowdango.bijouk.presenter.first

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withContext
import com.snowdango.bijouk.features.device.DeviceScreen
import com.snowdango.bijouk.features.setting.SettingScreen
import com.snowdango.bijouk.features.setting.view.AppInfoScreen
import com.snowdango.bijouk.features.setting.view.OSSLicenseScreen
import com.snowdango.bijouk.presenter.second.SecondActivity
import com.snowdango.bijouk.presenter.second.SecondActivityData
import com.snowdango.bijouk.ui.BijouKTheme

class FirstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            BijouKTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = FirstRoute.DEVICE,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        composable<FirstRoute.DEVICE> {
                            DeviceScreen(
                                onClickDevice = { data ->
                                    SecondActivity.start(
                                        context = context,
                                        secondActivityData = SecondActivityData(
                                            name = data.name,
                                            baseUrl = data.baseUrl,
                                            token = data.token,
                                        ),
                                    )
                                }
                            )
                        }
                        composable<FirstRoute.SETTING> {
                            SettingScreen(
                                onClickLicense = {
                                    navController.navigate(FirstRoute.OSS_LICENSE)
                                },
                                onClickAppInfo = {
                                    navController.navigate(FirstRoute.APP_INFO)
                                }
                            )
                        }
                        composable<FirstRoute.OSS_LICENSE> {
                            OSSLicenseScreen(
                                libs = Libs.Builder().withContext(LocalContext.current).build()
                            )
                        }
                        composable<FirstRoute.APP_INFO> {
                            AppInfoScreen()
                        }
                    }
                    val currentRouteState = navController.currentBackStackEntryAsState()
                    val route = FirstRoute.fromNavBackStackEntry(currentRouteState.value)
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
        BottomRoute(FirstRoute.DEVICE, Icons.Default.Computer, "Devices"),
        BottomRoute(FirstRoute.SETTING, Icons.Default.Settings, "Settings"),
    )

    data class BottomRoute(
        val route: FirstRoute,
        val icon: ImageVector,
        val name: String,
    )
}
