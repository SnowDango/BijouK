package com.snowdango.bijouk.domain.api.rpc.response

import com.snowdango.bijouk.domain.api.entity.rpc.RPCArtwork
import com.snowdango.bijouk.domain.api.entity.rpc.RPCPlayParameters
import com.snowdango.bijouk.domain.api.entity.rpc.RPCPreview
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class NowPlayingResponse(
    val info: NowPlayingResponseData,
    val status: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class NowPlayingResponseData(
    val albumName: String,
    val artistName: String,
    val artwork: RPCArtwork? = null,
    val audioLocale: String? = null,
    val audioTraits: List<String>? = null,
    val composerName: String? = null,
    val currentPlaybackTime: Double,
    val discNumber: Int?,
    val durationInMillis: Int,
    val genreNames: List<String>?,
    val hasLyrics: Boolean,
    val hasTimeSyncedLyrics: Boolean? = null,
    val inFavorites: Boolean,
    val inLibrary: Boolean,
    val isAppleDigitalMaster: Boolean? = null,
    val isMasteredForItunes: Boolean? = null,
    val isVocalAttenuationAllowed: Boolean? = null,
    val isrc: String? = null,
    val name: String,
    val playParams: RPCPlayParameters?,
    val previews: List<RPCPreview>?,
    val releaseDate: String? = null,
    val remainingTime: Double,
    val repeatMode: Int,
    val shuffleMode: Int,
    val trackNumber: Int?,
    val url: String? = null,
)