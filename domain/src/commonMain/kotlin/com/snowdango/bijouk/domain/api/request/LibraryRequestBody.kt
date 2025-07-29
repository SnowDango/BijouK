package com.snowdango.bijouk.domain.api.request

data class LibraryRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            type: LibraryType,
            limit: Int = 20,
            offset: Int = 20,
        ): SearchRequestBody {
            return SearchRequestBody(
                path = "/v1/me/library/${type.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        }
    }

    enum class LibraryType(val type: String) {
        SONGS("songs"),
        ALBUMS("albums"),
        PLAYLISTS("playlists"),
        ARTISTS("artists"),
    }
}
