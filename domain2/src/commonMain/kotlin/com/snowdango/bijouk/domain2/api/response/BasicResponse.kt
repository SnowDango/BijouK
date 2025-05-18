package com.snowdango.bijouk.domain2.api.response

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class BasicResponse(
    val status: String,
)
