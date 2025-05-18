package com.snowdango.bijouk.domain2.api.entity

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
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistAttributes(
    val artwork: Artwork,
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
