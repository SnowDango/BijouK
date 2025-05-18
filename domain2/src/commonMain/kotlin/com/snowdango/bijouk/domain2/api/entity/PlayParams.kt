package com.snowdango.bijouk.domain2.api.entity


import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class PlayParams(
    val id: String,
    val kind: String,
    val isLibrary: Boolean? = null,
    val reporting: Boolean? = null,
    val catalogId: String? = null,
    val versionHash: String? = null,
    val reportingId: String? = null,
)
