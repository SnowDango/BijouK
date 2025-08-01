package com.snowdango.bijouk.domain.api.socket.event

import com.snowdango.bijouk.domain.api.entity.rpc.RPCArtwork
import com.snowdango.bijouk.domain.api.entity.rpc.RPCPlayParameters
import com.snowdango.bijouk.domain.api.entity.rpc.RPCPreview
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class NowPlayingItemDidChangeEvent(
    val `data`: NowPlayingItemDidChangeEventData,
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class NowPlayingItemDidChangeEventData(
    val albumName: String,
    val artistName: String,
    val artwork: RPCArtwork? = null,
    val audioLocale: String? = null,
    val audioTraits: List<String>? = null,
    val composerName: String? = null,
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
    val playParams: RPCPlayParameters,
    val previews: List<RPCPreview>,
    val releaseDate: String? = null,
    val trackNumber: Int,
    val currentPlaybackTime: Double? = null,
    val remainingTime: Double? = null,
    val url: String? = null,
    val contentRating: String? = null,
)
