package com.snowdango.bijouk.domain.api.music.response

import com.snowdango.bijouk.domain.api.entity.AlbumData
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistSingleResponse(
    val data: ArtistSinglesResponseData,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistSinglesResponseData(
    val data: List<AlbumData>? = null,
)
