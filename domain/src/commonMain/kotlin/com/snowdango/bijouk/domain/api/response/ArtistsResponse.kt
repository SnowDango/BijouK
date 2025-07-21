package com.snowdango.bijouk.domain.api.response

import com.snowdango.bijouk.domain.api.entity.ArtistsData
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsResponse(
    val `data`: List<ArtistsData>
)
