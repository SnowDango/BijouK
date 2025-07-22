package com.snowdango.bijouk.domain.api.response

import com.snowdango.bijouk.domain.api.response.data.ArtistSinglesResponseData
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistSingleResponse(
    val data: ArtistSinglesResponseData,
)
