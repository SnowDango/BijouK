package com.snowdango.bijouk.domain.api.entity


import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Songs(
    val `data`: List<SongData>,
    val href: String,
    val next: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SongData(
    val attributes: SongAttributes,
    val href: String,
    val id: String,
    val meta: SongMeta,
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SongAttributes(
    val albumName: String,
    val artistName: String,
    val artwork: Artwork,
    val attribution: String? = null,
    val audioLocale: String,
    val audioTraits: List<String>,
    val composerName: String? = null,
    val discNumber: Int,
    val durationInMillis: Int,
    val genreNames: List<String>,
    val hasLyrics: Boolean,
    val hasTimeSyncedLyrics: Boolean,
    val isAppleDigitalMaster: Boolean,
    val isMasteredForItunes: Boolean,
    val isVocalAttenuationAllowed: Boolean,
    val isrc: String,
    val name: String,
    val playParams: PlayParams,
    val previews: List<Preview>,
    val releaseDate: String,
    val trackNumber: Int,
    val url: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SongMeta(
    val contentVersion: ContentVersion,
    val formerIds: List<String>? = null,
)
