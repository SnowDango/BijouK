package com.snowdango.bijouk.domain.api.response


import com.snowdango.bijouk.domain.api.response.data.ArtistsResponseData
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistsResponse(
    val `data`: ArtistsResponseData,
)



