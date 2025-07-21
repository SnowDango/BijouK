package com.snowdango.bijouk.domain.api.request

import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistDetailsRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            artistId: String,
            viewsTypes: List<ViewsType> = ViewsType.entries.toList(),
        ): SearchRequestBody {
            return SearchRequestBody(
                path = "/v1/catalog/jp/artists${artistId}?" +
                        "&views=${viewsTypes.joinToString(",") { it.type }}"
            )
        }
    }

    enum class ViewsType(val type: String) {
        TopSongs("top-songs"),
        Singles("singles"),
        FullAlbums("full-albums"),
    }
}
