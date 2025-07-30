package com.snowdango.bijouk.domain.api.music.response

import com.snowdango.bijouk.domain.api.entity.ArtistsData
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsResponse(
    val `data`: ArtistsResponseData,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsResponseData(
    val data: List<ArtistsData>,
)


