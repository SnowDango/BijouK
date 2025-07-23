package com.snowdango.bijouk.domain.api.response

import com.snowdango.bijouk.domain.api.response.data.ArtistFullAlbumResponseData
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class ArtistFullAlbumResponse(
    val data: ArtistFullAlbumResponseData,
)
