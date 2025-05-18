package com.snowdango.bijouk.domain.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class ContentVersion(
    @SerialName("MZ_INDEXER")
    val mzIndexer: Long,
    @SerialName("RTCI")
    val rtci: Long,
)
