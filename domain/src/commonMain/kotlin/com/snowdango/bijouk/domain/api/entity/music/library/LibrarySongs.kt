package com.snowdango.bijouk.domain.api.entity.music.library

import com.snowdango.bijouk.domain.api.entity.music.Artwork
import com.snowdango.bijouk.domain.api.entity.music.PlayParameters
import kotlinx.serialization.Serializable


@Serializable
data class LibrarySongs(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes? = null,
    val relationships: Relationships? = null,
) {
    @Serializable
    data class Attributes(
        val albumName: String,
        val artistName: String,
        val artwork: Artwork,
        val contentRating: String? = null,
        val discNumber: Int? = null,
        val durationInMillis: Long,
        val genreNames: List<String>,
        val hasLyrics: Boolean,
        val inFavorites: Boolean? = null,
        val name: String,
        val playParams: PlayParameters? = null,
        val releaseDate: String? = null,
        val trackNumber: Int? = null,
    )

    @Serializable
    data object Relationships
}
