package com.snowdango.bijouk.domain.api.response.data

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.entity.Artwork
import com.snowdango.bijouk.domain.api.entity.PlayParams
import com.snowdango.bijouk.domain.api.entity.Preview
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Info(
    val albumName: String,
    val artistName: String,
    val artwork: Artwork,
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
    val playParams: PlayParams?,
    val previews: List<Preview>?,
    val releaseDate: String? = null,
    val remainingTime: Double,
    val repeatMode: Int,
    val shuffleMode: Int,
    val trackNumber: Int?,
    val url: String? = null,
)