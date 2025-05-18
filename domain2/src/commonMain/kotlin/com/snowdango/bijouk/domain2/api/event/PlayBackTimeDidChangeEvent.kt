package com.snowdango.bijouk.domain2.api.event

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class PlayBackTimeDidChangeEvent(
    val `data`: PlayBackTimeDidChangeEventData,
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class PlayBackTimeDidChangeEventData(
    val currentPlaybackDuration: Double,
    val currentPlaybackTime: Double,
    val currentPlaybackTimeRemaining: Double,
    val isPlaying: Boolean
)
