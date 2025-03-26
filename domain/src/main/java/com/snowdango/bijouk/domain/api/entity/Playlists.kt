package com.snowdango.bijouk.domain.api.entity

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Playlists(
    val `data`: List<PlaylistData>,
    val href: String
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PlaylistData(
    val attributes: PlaylistAttributes,
    val href: String,
    val id: String,
    val type: String
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PlaylistAttributes(
    val artwork: Artwork,
    val audioTraits: List<String>? = null,
    val curatorName: String,
    val description: PlaylistDescription,
    val editorialNotes: EditorialNotes,
    val hasCollaboration: Boolean,
    val isChart: Boolean,
    val lastModifiedDate: String,
    val name: String,
    val playParams: PlayParams,
    val playlistType: String,
    val supportsSing: Boolean,
    val url: String
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PlaylistDescription(
    val short: String,
    val standard: String
)
