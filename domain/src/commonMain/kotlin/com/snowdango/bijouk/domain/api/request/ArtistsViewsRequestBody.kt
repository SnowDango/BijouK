package com.snowdango.bijouk.domain.api.request

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsViewsRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            artistId: String,
            viewType: ViewType,
            limit: Int,
            offset: Int,
        ): ArtistsViewsRequestBody {
            return ArtistsViewsRequestBody(
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
