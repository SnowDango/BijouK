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
    data object LIBRARY : SecondRoute()

    @Serializable
    data class ALBUM(
        val albumId: String,
        val isLibrary: Boolean,
    ) : SecondRoute()

    @Serializable
    data class PLAYLIST(
        val isLibrary: Boolean,
        val playlistId: String,
    ) : SecondRoute()

    @Serializable
    data class ARTIST(
        val artistId: String,
        val isLibrary: Boolean,
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

                in ALBUM.serializer().descriptor.serialName -> {
                    navBackStackEntry.toRoute<ALBUM>()
                }

                in LIBRARY.serializer().descriptor.serialName -> {
                    navBackStackEntry.toRoute<LIBRARY>()
                }

                in PLAYLIST.serializer().descriptor.serialName -> {
                    navBackStackEntry.toRoute<PLAYLIST>()
                }

                else -> null
            }
        }
    }
}
