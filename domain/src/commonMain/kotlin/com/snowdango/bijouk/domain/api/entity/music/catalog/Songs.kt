package com.snowdango.bijouk.domain.api.entity.music.catalog

import com.snowdango.bijouk.domain.api.entity.music.Artwork
import com.snowdango.bijouk.domain.api.entity.music.PlayParameters
import kotlinx.serialization.Serializable

@Serializable
data class Songs(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes,
    val relationships: Relationships? = null,
) {
    @Serializable
    data class Attributes(
        val albumName: String,
        val artistName: String,
        val artistUrl: String? = null,
        val artwork: Artwork,
        val attribution: String? = null,
        val composerName: String? = null,
        val contentRating: String? = null,
        val discNumber: Int? = null,
        val durationInMillis: Long,
        val editorialNotes: EditorialNotes? = null,
        val genreNames: List<String>,
        val hasLyrics: Boolean,
        val inFavorites: Boolean? = null,
        val isAppleDigitalMaster: Boolean,
        val isrc: String? = null,
        val movementCount: Int? = null,
        val movementName: String? = null,
        val movementNumber: Int? = null,
        val name: String,
        val playParams: PlayParameters? = null,
        val previews: List<Preview>,
        val releaseDate: String? = null,
        val trackNumber: Int? = null,
        val url: String,
        val workName: String? = null,
    )

    @Serializable
    data object Relationships
}
