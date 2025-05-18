package com.snowdango.bijouk.domain2.api.event

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class NowPlayingStatusDidChange(
    val `data`: NowPlayingStatusDidChangeData,
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class NowPlayingStatusDidChangeData(
    val inFavorites: Boolean,
    val inLibrary: Boolean
)
