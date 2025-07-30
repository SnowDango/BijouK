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
    val relationships: ArtistRelationships? = null,
    val type: String,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistAttributes(
    val artwork: Artwork? = null,
    val classicalUrl: String? = null,
    val genreNames: List<String>? = null,
    val name: String,
    val url: String? = null,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistRelationships(
    val albums: Albums,
    @SerialName("default-playable-content")
    val stations: Stations? = null,
)
