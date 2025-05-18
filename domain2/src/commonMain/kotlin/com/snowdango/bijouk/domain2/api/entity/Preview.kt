package com.snowdango.bijouk.domain2.api.entity


import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Preview(
    val url: String? = null,
)
