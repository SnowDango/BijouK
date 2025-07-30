package com.snowdango.bijouk.domain.api.response.data

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class LibraryResponseData<T>(
    val next: String? = null,
    val `data`: List<T>? = null,
)
