package com.snowdango.bijouk.domain.api.response

import com.snowdango.bijouk.domain.api.response.data.ArtistsTopSongResponseData
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsTopSongResponse(
    val `data`: ArtistsTopSongResponseData,
)
