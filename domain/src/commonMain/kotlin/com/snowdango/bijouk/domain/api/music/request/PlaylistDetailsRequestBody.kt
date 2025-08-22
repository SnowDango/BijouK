package com.snowdango.bijouk.domain.api.music.request

import kotlinx.serialization.Serializable


@Serializable
data class PlaylistDetailsRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            isLibrary: Boolean,
            playlistId: String,
        ): PlaylistDetailsRequestBody {
            return PlaylistDetailsRequestBody(
                path = if (isLibrary) {
                    "/v1/me/library/playlists/$playlistId"
                } else {
                    "/v1/catalog/jp/playlists/$playlistId"
                }
            )
        }
    }
}
