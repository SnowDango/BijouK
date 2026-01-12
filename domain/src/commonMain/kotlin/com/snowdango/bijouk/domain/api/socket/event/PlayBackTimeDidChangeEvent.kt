package com.snowdango.bijouk.domain.api.socket.event

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
