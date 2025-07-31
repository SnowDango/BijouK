package com.snowdango.bijouk.domain.api.music.response

import com.snowdango.bijouk.domain.api.entity.music.library.LibraryAlbums
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryArtists
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryPlaylists
import com.snowdango.bijouk.domain.api.entity.music.library.LibrarySongs
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LibrarySearchResponse(
    val `data`: Data,
) {
    @Serializable
    data class Data(
        val results: Results
    ) {
        @Serializable
        data class Results(
            @SerialName("library-albums")
            val albums: LibrarySearchResult<LibraryAlbums>? = null,
            @SerialName("library-artists")
            val artists: LibrarySearchResult<LibraryArtists>? = null,
            @SerialName("library-playlists")
            val playlists: LibrarySearchResult<LibraryPlaylists>? = null,
            @SerialName("library-songs")
            val songs: LibrarySearchResult<LibrarySongs>? = null,
        ) {
            @Serializable
            data class LibrarySearchResult<T>(
                val next: String? = null,
                val href: String? = null,
                val data: List<T>,
            )
        }
    }
}