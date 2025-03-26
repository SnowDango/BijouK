package com.snowdango.bijouk.domain.api.entity

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
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
