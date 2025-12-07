package com.snowdango.bijouk.domain.api.music.request

import kotlinx.serialization.Serializable


@Serializable
data class AlbumRelationshipRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            albumId: String,
            viewType: ViewType,
            limit: Int,
            offset: Int,
        ): AlbumRelationshipRequestBody {
            return AlbumRelationshipRequestBody(
                path = "/v1/catalog/jp/albums/${albumId}/${viewType.value}?" +
                        "limit=${limit}" +
                        "&offset=${offset}",
            )
        }
    }

    enum class ViewType(val value: String) {
        TRACKS("tracks"),
    }
}
