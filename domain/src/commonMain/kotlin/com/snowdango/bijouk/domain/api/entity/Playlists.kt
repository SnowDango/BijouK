package com.snowdango.bijouk.domain.api.entity


import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Playlists(
    val `data`: List<PlaylistData>,
    val href: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class PlaylistData(
    val attributes: PlaylistAttributes,
    val href: String,
    val id: String,
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class PlaylistAttributes(
    val artwork: Artwork? = null,
    val audioTraits: List<String>? = null,
    val curatorName: String,
    val description: PlaylistDescription? = null,
    val editorialNotes: EditorialNotes? = null,
    val hasCollaboration: Boolean,
    val isChart: Boolean,
    val lastModifiedDate: String,
    val name: String,
    val playParams: PlayParams,
    val playlistType: String,
    val supportsSing: Boolean,
    val url: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class PlaylistDescription(
    val short: String? = null,
    val standard: String
)
