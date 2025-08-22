package com.snowdango.bijouk.domain.api.music.request

import net.thauvin.erik.urlencoder.UrlEncoderUtil

data class InLibrarySearchRequestBody(
    val path: String,
) {
    companion object {
        fun create(
            search: String,
            searchTypes: List<InLibrarySearchType>,
            limit: Int = 20,
            offset: Int = 20,
        ): SearchRequestBody {
            return SearchRequestBody(
                path = "/v1/me/library/search?" +
                        "term=${UrlEncoderUtil.encode(search)}" +
                        "&types=${searchTypes.joinToString(",") { it.type }}" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
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