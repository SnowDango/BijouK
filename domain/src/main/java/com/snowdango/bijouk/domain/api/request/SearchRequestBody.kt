package com.snowdango.bijouk.domain.api.request

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import java.net.URLEncoder

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            search: String,
            searchTypes: List<SearchType> = SearchType.entries.toList(),
            limit: Int = 20,
        ): SearchRequestBody {
            return SearchRequestBody(
                path = "/v1/catalog/jp/search?" +
                    "term=${URLEncoder.encode(search, "utf-8")}" +
                    "&types=${searchTypes.joinToString(",") { it.type }}" +
                    "&limit=$limit"
            )
        }
    }

    enum class SearchType(val type: String) {
        Songs("songs"),
        Playlists("playlists"),
        Albums("albums"),
        Artists("artists"),
    }
}
