package com.snowdango.bijouk.domain.api.request.artist

import com.snowdango.bijouk.domain.api.request.SearchRequestBody
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistDetailsRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            artistId: String,
        ): SearchRequestBody {
            return SearchRequestBody(
                path = "/v1/catalog/jp/artists/${artistId}"
            )
        }
    }
}
