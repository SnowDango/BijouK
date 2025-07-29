package com.snowdango.bijouk.domain.api.entity

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Albums(
    val `data`: List<AlbumData>,
    val href: String,
    val next: String? = null,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class AlbumData(
    val attributes: AlbumAttributes? = null,
    val href: String,
    val id: String,
    val meta: AlbumMetaData? = null,
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class AlbumMetaData(
    val contentVersion: ContentVersion,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class AlbumAttributes(
    val artistName: String,
    val artwork: Artwork? = null,
    val audioTraits: List<String>? = null,
    val classicalUrl: String? = null,
    val copyright: String? = null,
    val editorialNotes: EditorialNotes? = null,
    val genreNames: List<String>,
    val isCompilation: Boolean? = null,
    val isComplete: Boolean? = null,
    val isMasteredForItunes: Boolean? = null,
    val isPrerelease: Boolean? = null,
    val isSingle: Boolean? = null,
    val name: String,
    val playParams: PlayParams,
    val recordLabel: String? = null,
    val releaseDate: String? = null,
    val trackCount: Int,
    val upc: String? = null,
    val url: String? = null,
)
