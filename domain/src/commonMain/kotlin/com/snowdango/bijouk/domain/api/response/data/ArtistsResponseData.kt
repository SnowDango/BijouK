package com.snowdango.bijouk.domain.api.response.data

import com.snowdango.bijouk.domain.api.entity.ArtistsData
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsResponseData(
    val data: List<ArtistsData>,
)
