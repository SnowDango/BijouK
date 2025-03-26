package com.snowdango.bijouk.domain.api.entity

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Artists(
    val `data`: List<ArtistsData>,
    val href: String,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ArtistsData(
    val attributes: ArtistAttributes,
    val href: String,
    val id: String,
    val relationships: ArtistRelationships,
    val type: String
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ArtistAttributes(
    val artwork: Artwork,
    val classicalUrl: String,
    val genreNames: List<String>,
    val name: String,
    val url: String
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ArtistRelationships(
    val albums: Albums
)
