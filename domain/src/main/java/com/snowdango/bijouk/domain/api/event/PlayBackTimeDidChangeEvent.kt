package com.snowdango.bijouk.domain.api.event

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PlayBackTimeDidChangeEvent(
    val `data`: PlayBackTimeDidChangeEventData,
    val type: String
)


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PlayBackTimeDidChangeEventData(
    val currentPlaybackDuration: Double,
    val currentPlaybackTime: Double,
    val currentPlaybackTimeRemaining: Double,
    val isPlaying: Boolean
)