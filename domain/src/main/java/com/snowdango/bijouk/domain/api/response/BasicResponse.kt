package com.snowdango.bijouk.domain.api.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class BasicResponse(
    val status: String,
)