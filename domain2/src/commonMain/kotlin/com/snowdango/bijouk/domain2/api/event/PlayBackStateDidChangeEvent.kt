package com.snowdango.bijouk.domain2.api.event

import com.snowdango.bijouk.domain2.api.entity.Artwork
import com.snowdango.bijouk.domain2.api.entity.PlayParams
import com.snowdango.bijouk.domain2.api.entity.Preview
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class PlayBackStateDidChangeEvent(
    val `data`: PlayBackStateDidChangeEventData,
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class PlayBackStateDidChangeEventData(
    val attributes: Attributes? = null,
    val state: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Attributes(
    val albumName: String,
    val artistName: String,
    val artwork: Artwork,
    val audioLocale: String? = null,
    val audioTraits: List<String>? = null,
    val composerName: String? = null,
    val currentPlaybackTime: Double,
    val discNumber: Int,
    val durationInMillis: Int,
    val genreNames: List<String>,
    val hasLyrics: Boolean,
    val hasTimeSyncedLyrics: Boolean? = null,
    val isAppleDigitalMaster: Boolean? = null,
    val isMasteredForItunes: Boolean? = null,
    val isVocalAttenuationAllowed: Boolean? = null,
    val isrc: String? = null,
    val name: String,
    val playParams: PlayParams,
    val previews: List<Preview>,
    val releaseDate: String? = null,
    val remainingTime: Double,
    val trackNumber: Int,
    val url: String? = null,
    val reportingId: String? = null,
)
