package com.snowdango.bijouk.domain.api.response.data

import com.snowdango.bijouk.domain.api.entity.AlbumData
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistSinglesResponseData(
    val data: List<AlbumData>? = null,
)
