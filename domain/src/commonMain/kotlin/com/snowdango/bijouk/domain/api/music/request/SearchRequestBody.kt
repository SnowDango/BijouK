package com.snowdango.bijouk.domain.api.music.request

import kotlinx.serialization.Serializable
import net.thauvin.erik.urlencoder.UrlEncoderUtil

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            search: String,
            searchTypes: List<SearchType> = SearchType.entries.toList(),
            limit: Int = 20,
            offset: Int = 0,
        ): SearchRequestBody {
            return SearchRequestBody(
                path = "/v1/catalog/jp/search?" +
                        "term=${UrlEncoderUtil.encode(search)}" +
                        "&types=${searchTypes.joinToString(",") { it.type }}" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        }
    }

    enum class SearchType(val type: String) {
        Songs("songs"),
        Albums("albums"),
        Artists("artists"),
    }
}