package com.snowdango.bijouk.domain.api.music.request


import kotlinx.serialization.Serializable

@Serializable
data class LibraryArtistRelationshipRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            artistId: String,
            viewType: ViewType,
            limit: Int,
            offset: Int,
        ): LibraryArtistRelationshipRequestBody {
            return LibraryArtistRelationshipRequestBody(
                path = "/v1/me/library/artists/${artistId}/${viewType.value}?include=catalog" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        }
    }

    enum class ViewType(val value: String) {
        ALBUMS("albums"),
    }
}
