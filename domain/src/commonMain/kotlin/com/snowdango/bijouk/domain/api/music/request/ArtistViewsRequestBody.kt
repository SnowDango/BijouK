package com.snowdango.bijouk.domain.api.music.request

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistViewsRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            artistId: String,
            viewType: ViewType,
            limit: Int,
            offset: Int,
        ): ArtistViewsRequestBody {
            return ArtistViewsRequestBody(
                path = "/v1/catalog/jp/artists/${artistId}/view/${viewType.value}?" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        }
    }

    enum class ViewType(val value: String) {
        Singles("singles"),
        FullAlbums("full-albums"),
        TopSongs("top-songs"),
    }
}