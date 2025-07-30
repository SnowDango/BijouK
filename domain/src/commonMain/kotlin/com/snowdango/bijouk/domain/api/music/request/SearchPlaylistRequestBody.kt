package com.snowdango.bijouk.domain.api.music.request

import kotlinx.serialization.Serializable
import net.thauvin.erik.urlencoder.UrlEncoderUtil

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchPlaylistRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            search: String,
            limit: Int = 20,
            offset: Int = 0,
        ): SearchRequestBody {
            return SearchRequestBody(
                path = "/v1/social/jp/search?" +
                        "term=${UrlEncoderUtil.encode(search)}" +
                        "&types=playlists" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        }
    }
}