package com.snowdango.bijouk.domain.api.music.response

import com.snowdango.bijouk.domain.api.entity.SongData
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsTopSongResponse(
    val `data`: ArtistsTopSongResponseData,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsTopSongResponseData(
    val next: String? = null,
    val data: List<SongData>? = null,
)
