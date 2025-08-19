package com.snowdango.bijouk.domain.api.music.request

import kotlinx.serialization.Serializable


@Serializable
data class PlaylistTracksRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            isLibrary: Boolean,
            playlistId: String,
            limit: Int = 20,
            offset: Int = 0,
        ): PlaylistTracksRequestBody {
            val path = if (isLibrary) {
                "/v1/me/library/playlists/$playlistId"
            } else {
                "/v1/catalog/jp/playlists/$playlistId"
            }
            return PlaylistTracksRequestBody(
                path = "$path/tracks?" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        }
    }
}
