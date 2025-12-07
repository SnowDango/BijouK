package com.snowdango.bijouk.domain.api.entity.music.library

import com.snowdango.bijouk.domain.api.entity.music.PlayParameters
import com.snowdango.bijouk.domain.api.entity.music.catalog.Albums
import kotlinx.serialization.Serializable

@Serializable
data class LibraryAlbums(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes? = null,
    val relationships: Relationships? = null,
) {
    @Serializable
    data class Attributes(
        val artistName: String,
        val artwork: LibraryArtwork? = null,
        val contentRating: String? = null,
        val dateAdded: String? = null,
        val name: String,
        val playParams: PlayParameters? = null,
        val releaseDate: String? = null,
        val trackCount: Int,
        val genreNames: List<String>,
        val inFavorites: Boolean? = null,
    )

    @Serializable
    data class Relationships(
        val catalog: Catalog? = null,
    ) {
        @Serializable
        data class Catalog(
            val href: String? = null,
            val data: List<Albums>,
        )
    }
}
