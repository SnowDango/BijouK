package com.snowdango.bijouk.domain.api.music.request


data class LibraryPlaylistFolderRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            folderId: String,
            limit: Int = 20,
            offset: Int = 20,
        ): SearchRequestBody {
            return SearchRequestBody(
                path = "/v1/me/library/playlist-folders/${folderId}/children?" +
                        "limit=$limit" +
                        "&offset=$offset"
            )
        }
    }
}
