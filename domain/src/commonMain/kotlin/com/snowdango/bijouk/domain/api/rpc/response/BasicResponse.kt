package com.snowdango.bijouk.domain.api.rpc.response

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class BasicResponse(
    val status: String,
)