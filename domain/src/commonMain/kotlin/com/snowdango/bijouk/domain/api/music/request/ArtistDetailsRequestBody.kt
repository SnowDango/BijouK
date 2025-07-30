package com.snowdango.bijouk.domain.api.music.request

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistDetailsRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            artistId: String,
        ): ArtistDetailsRequestBody {
            return ArtistDetailsRequestBody(
                path = "/v1/catalog/jp/artists/${artistId}?" +
                        "include=default-playable-content"
            )
        }
    }
}