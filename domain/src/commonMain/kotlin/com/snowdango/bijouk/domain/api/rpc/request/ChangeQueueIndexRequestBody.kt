package com.snowdango.bijouk.domain.api.rpc.request

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ChangeQueueIndexRequestBody(
    val index: Int,
)