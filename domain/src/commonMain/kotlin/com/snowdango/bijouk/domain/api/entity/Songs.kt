package com.snowdango.bijouk.domain.api.entity


import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Songs(
    val `data`: List<SongData>,
    val href: String,
    val next: String? = null,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SongData(
    val attributes: SongAttributes? = null,
    val href: String,
    val id: String,
    val meta: SongMeta? = null,
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SongAttributes(
    val albumName: String? = null,
    val artistName: String? = null,
    val artwork: Artwork,
    val attribution: String? = null,
    val audioLocale: String? = null,
    val audioTraits: List<String>? = null,
    val composerName: String? = null,
    val discNumber: Int? = null,
    val durationInMillis: Int,
    val genreNames: List<String>,
    val hasLyrics: Boolean,
    val hasTimeSyncedLyrics: Boolean? = null,
    val isAppleDigitalMaster: Boolean,
    val isMasteredForItunes: Boolean? = null,
    val isVocalAttenuationAllowed: Boolean? = null,
    val isrc: String? = null,
    val name: String,
    val playParams: PlayParams? = null,
    val previews: List<Preview>,
    val releaseDate: String? = null,
    val trackNumber: Int? = null,
    val url: String,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SongMeta(
    val contentVersion: ContentVersion,
    val formerIds: List<String>? = null,
)
