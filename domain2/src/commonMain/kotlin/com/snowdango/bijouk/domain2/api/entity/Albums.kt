package com.snowdango.bijouk.domain2.api.entity

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
    val artwork: Artwork,
    val audioTraits: List<String>,
    val classicalUrl: String? = null,
    val copyright: String,
    val editorialNotes: EditorialNotes? = null,
    val genreNames: List<String>,
    val isCompilation: Boolean,
    val isComplete: Boolean,
    val isMasteredForItunes: Boolean,
    val isPrerelease: Boolean,
    val isSingle: Boolean,
    val name: String,
    val playParams: PlayParams,
    val recordLabel: String,
    val releaseDate: String,
    val trackCount: Int,
    val upc: String,
    val url: String
)
