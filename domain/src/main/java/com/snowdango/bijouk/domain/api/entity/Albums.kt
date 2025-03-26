package com.snowdango.bijouk.domain.api.entity

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Albums(
    val `data`: List<AlbumData>,
    val href: String,
    val next: String? = null,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AlbumData(
    val attributes: AlbumAttributes? = null,
    val href: String,
    val id: String,
    val meta: AlbumMetaData? = null,
    val type: String
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AlbumMetaData(
    val contentVersion: ContentVersion,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AlbumAttributes(
    val artistName: String,
    val artwork: Artwork,
    val audioTraits: List<String>,
    val classicalUrl: String,
    val copyright: String,
    val editorialNotes: EditorialNotes,
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
