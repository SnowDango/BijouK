package com.snowdango.bijouk.domain.api.entity.rpc

import kotlinx.serialization.Serializable

@Serializable
data class RPCPreview(
    val url: String? = null,
)