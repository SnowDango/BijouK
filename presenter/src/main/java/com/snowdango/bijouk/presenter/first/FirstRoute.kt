package com.snowdango.bijouk.presenter.first

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

sealed class FirstRoute {
    @Serializable
    object DEVICE : FirstRoute()

    @Serializable
    object SETTING : FirstRoute()

    @Serializable
    object OSS_LICENSE : FirstRoute()

    @Serializable
    object APP_INFO : FirstRoute()

    @Serializable
    data object QR_SCAN : FirstRoute()

    companion object {
        @Composable
        fun fromNavBackStackEntry(navBackStackEntry: NavBackStackEntry?): FirstRoute? {
            if (navBackStackEntry?.destination?.route == null) return null
            return when (navBackStackEntry.destination.route!!) {
                in DEVICE.serializer().descriptor.serialName -> {
                    navBackStackEntry.toRoute<DEVICE>()
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

                in QR_SCAN.serializer().descriptor.serialName -> {
                    navBackStackEntry.toRoute<QR_SCAN>()
                }

                else -> null
            }
        }
    }
}
