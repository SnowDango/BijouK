package com.snowdango.bijouk.domain.api.rpc.response

import kotlinx.serialization.Serializable

@Serializable
data class ShuffleResponse(
    val status: String,
    val value: Int,
)
