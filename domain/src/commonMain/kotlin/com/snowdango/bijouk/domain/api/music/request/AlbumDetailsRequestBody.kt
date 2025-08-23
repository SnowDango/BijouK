package com.snowdango.bijouk.domain.api.music.request

import kotlinx.serialization.Serializable


@Serializable
data class AlbumDetailsRequestBody(
    val path: String,
) {

    companion object {
        fun create(
            albumId: String,
            isLibrary: Boolean,
        ): AlbumDetailsRequestBody {
            return AlbumDetailsRequestBody(
                path = if (isLibrary) {
                    "/v1/me/library/albums/$albumId"
                } else {
                    "/v1/catalog/jp/albums/$albumId"
                }
            )
        }
    }
}