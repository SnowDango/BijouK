package com.snowdango.bijouk.domain.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Artists(
    val `data`: List<ArtistsData>,
    val href: String,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsData(
    val attributes: ArtistAttributes,
    val href: String,
    val id: String,
    val relationships: ArtistRelationships,
    val type: String,
    val views: ArtistViews? = null
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistAttributes(
    val artwork: Artwork? = null,
    val classicalUrl: String? = null,
    val genreNames: List<String>,
    val name: String,
    val url: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistRelationships(
    val albums: Albums
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistViews(
    @SerialName("top-songs")
    val topSongs: TopSongs? = null,
    val singles: Singles? = null,
    @SerialName("full-albums")
    val fullAlbums: FullAlbums? = null,
)
