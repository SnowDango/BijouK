package com.snowdango.bijouk.domain.api.response

import com.snowdango.bijouk.domain.api.response.data.NowPlayingResponseData
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class NowPlayingResponse(
    val info: NowPlayingResponseData,
    val status: String
)
