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
            isLibrary: Boolean,
        ): ArtistDetailsRequestBody {
            return ArtistDetailsRequestBody(
                path = if (isLibrary) {
                    "/v1/me/library/artists/"
                } else {
                    "/v1/catalog/jp/artists/"
                } + "${artistId}?include=default-playable-content"
            )
        }
    }
}