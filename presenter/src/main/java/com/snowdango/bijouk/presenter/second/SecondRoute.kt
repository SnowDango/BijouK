package com.snowdango.bijouk.presenter.second

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

sealed class SecondRoute {

    @Serializable
    data object QUEUE : SecondRoute()

    @Serializable
    data object SEARCH : SecondRoute()

    @Serializable
    data class ARTIST(
        val artistId: String,
    ) : SecondRoute()

    companion object {
        @Composable
        fun fromNavBackStackEntry(navBackStackEntry: NavBackStackEntry?): SecondRoute? {
            if (navBackStackEntry?.destination?.route == null) return null
            return when (navBackStackEntry.destination.route!!) {
                in QUEUE.serializer().descriptor.serialName -> {
                    navBackStackEntry.toRoute<QUEUE>()
                }

                in SEARCH.serializer().descriptor.serialName -> {
                    navBackStackEntry.toRoute<SEARCH>()
                }

                in ARTIST.serializer().descriptor.serialName -> {
                    navBackStackEntry.toRoute<ARTIST>()
                }

                else -> null
            }
        }
    }
}
