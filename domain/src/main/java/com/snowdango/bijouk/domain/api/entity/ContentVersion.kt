package com.snowdango.bijouk.domain.api.entity

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ContentVersion(
    @SerialName("MZ_INDEXER")
    val mzIndexer: Long,
    @SerialName("RTCI")
    val rtci: Long,
)
