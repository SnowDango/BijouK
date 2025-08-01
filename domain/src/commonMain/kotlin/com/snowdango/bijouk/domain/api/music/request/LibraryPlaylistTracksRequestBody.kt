package com.snowdango.bijouk.domain.api.music.request

data class LibraryPlaylistTracksRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            playlistId: String,
            limit: Int = 20,
            offset: Int = 0,
        ): LibraryPlaylistTracksRequestBody {
            return LibraryPlaylistTracksRequestBody(
                path = "/v1/me/library/playlists/$playlistId/tracks?" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        }
    }
}
