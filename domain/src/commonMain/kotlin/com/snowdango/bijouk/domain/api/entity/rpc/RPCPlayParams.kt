package com.snowdango.bijouk.domain.api.entity.rpc

import kotlinx.serialization.Serializable

@Serializable
data class RPCPlayParams(
    val id: String,
    val kind: String,
    val isLibrary: Boolean? = null,
    val reporting: Boolean? = null,
    val catalogId: String? = null,
    val versionHash: String? = null,
    val reportingId: String? = null,
)