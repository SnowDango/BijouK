package com.snowdango.bijouk.domain.api.entity.music.library

import com.snowdango.bijouk.domain.api.entity.music.Artwork
import com.snowdango.bijouk.domain.api.entity.music.PlayParameters
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
        val artwork: Artwork,
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
    data object Relationships
}
