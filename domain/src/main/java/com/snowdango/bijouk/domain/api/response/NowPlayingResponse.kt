package com.snowdango.bijouk.domain.api.response

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.response.data.NowPlayingResponseData
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NowPlayingResponse(
    val info: NowPlayingResponseData,
    val status: String
)
