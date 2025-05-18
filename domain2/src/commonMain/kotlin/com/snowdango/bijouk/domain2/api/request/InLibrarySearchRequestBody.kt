package com.snowdango.bijouk.domain2.api.request

import java.net.URLEncoder

data class InLibrarySearchRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            search: String,
            searchTypes: List<InLibrarySearchType> = InLibrarySearchType.entries.toList(),
            limit: Int = 20,
        ): SearchRequestBody {
            return SearchRequestBody(
                path = "/v1/me/library/search?" +
                        "term=${URLEncoder.encode(search, "utf-8")}" +
                        "&types=${searchTypes.joinToString(",") { it.type }}" +
                        "&limit=$limit"
            )
        }
    }

    enum class InLibrarySearchType(val type: String) {
        Songs("library-songs"),
        Playlists("library-playlists"),
        Albums("library-albums"),
        Artists("library-artists"),
    }
}
