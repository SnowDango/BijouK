package com.snowdango.bijouk.domain.api.event

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NowPlayingStatusDidChange(
    val `data`: NowPlayingStatusDidChangeData,
    val type: String
)


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NowPlayingStatusDidChangeData(
    val inFavorites: Boolean,
    val inLibrary: Boolean
)