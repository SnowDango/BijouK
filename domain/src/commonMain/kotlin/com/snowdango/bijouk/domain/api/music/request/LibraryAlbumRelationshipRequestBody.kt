package com.snowdango.bijouk.domain.api.music.request

import kotlinx.serialization.Serializable


@Serializable
data class LibraryAlbumRelationshipRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            albumId: String,
            viewType: ViewType,
            limit: Int,
            offset: Int
        ): LibraryAlbumRelationshipRequestBody {
            return LibraryAlbumRelationshipRequestBody(
                path = "/v1/me/library/albums/${albumId}/${viewType.value}?include=catalog" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        }
    }

    enum class ViewType(val value: String) {
        TRACKS("tracks"),
    }
}
