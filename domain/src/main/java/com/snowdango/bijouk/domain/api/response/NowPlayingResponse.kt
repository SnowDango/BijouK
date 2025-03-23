package com.snowdango.bijouk.domain.api.response

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.response.data.Info
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NowPlayingResponse(
    val info: Info,
    val status: String
)