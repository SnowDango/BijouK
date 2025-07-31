package com.snowdango.bijouk.domain.api.music.response


import com.snowdango.bijouk.domain.api.entity.music.catalog.Albums
import com.snowdango.bijouk.domain.api.entity.music.catalog.Artists
import com.snowdango.bijouk.domain.api.entity.music.catalog.Playlists
import com.snowdango.bijouk.domain.api.entity.music.catalog.Songs
import kotlinx.serialization.Serializable


@Serializable
data class SearchResponse(
    val `data`: Data
) {
    @Serializable
    data class Data(
        val results: Results,
    ) {
        @Serializable
        data class Results(
            val albums: SearchResult<Albums>? = null,
            val artists: SearchResult<Artists>? = null,
            val playlists: SearchResult<Playlists>? = null,
            val songs: SearchResult<Songs>? = null,
        ) {
            @Serializable
            data class SearchResult<T>(
                val next: String? = null,
                val href: String? = null,
                val data: List<T>,
            )
        }
    }
}
