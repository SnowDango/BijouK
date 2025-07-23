package com.snowdango.bijouk

/*class MainActivity : ComponentActivity() {

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
                            NowPlayScreen(
                                nowPlay.name,
                                nowPlay.baseUrl,
                                nowPlay.token,
                                onClickArtist = { artistId ->
                                    navController.navigate(
                                        Route.ARTIST(
                                            artistId = artistId,
                                            baseUrl = nowPlay.baseUrl,
                                            token = nowPlay.token
                                        )
                                    )
                                },
                            )
                        }

                        composable<Route.ARTIST> { backStackEntry ->
                            val artist = backStackEntry.toRoute<Route.ARTIST>()
                            ArtistsDetailScreen(
                                artist.baseUrl,
                                artist.token,
                                artist.artistId,
                                sheetMinSize = 100.dp,
                                onNavigationBack = {
                                    navController.popBackStack()
                                },
                            )
                        }

                        composable<Route.SETTING> {
                            SettingScreen(
                                onClickLicense = {
                                    navController.navigate(Route.OSS_LICENSE)
                                },
                                onClickAppInfo = {
                                    navController.navigate(Route.APP_INFO)
                                }
                            )
                        }
                        composable<Route.OSS_LICENSE> {
                            OSSLicenseScreen(
                                libs = Libs.Builder().withContext(LocalContext.current).build()
                            )
                        }
                        composable<Route.APP_INFO> {
                            AppInfoScreen()
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
        data class ARTIST(
            val artistId: String,
            val baseUrl: String,
            val token: String,
        ) : Route()

        @Serializable
        object SETTING : Route()

        @Serializable
        object OSS_LICENSE : Route()

        @Serializable
        object APP_INFO : Route()

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

                    in ARTIST.serializer().descriptor.serialName -> {
                        navBackStackEntry.toRoute<ARTIST>()
                    }

                    in SETTING.serializer().descriptor.serialName -> {
                        navBackStackEntry.toRoute<SETTING>()
                    }

                    in OSS_LICENSE.serializer().descriptor.serialName -> {
                        navBackStackEntry.toRoute<OSS_LICENSE>()
                    }

                    in APP_INFO.serializer().descriptor.serialName -> {
                        navBackStackEntry.toRoute<APP_INFO>()
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
}*/
