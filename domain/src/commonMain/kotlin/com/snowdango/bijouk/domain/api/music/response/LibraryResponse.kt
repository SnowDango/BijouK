package com.snowdango.bijouk.domain.api.music.response

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class LibraryResponse<T>(
    val `data`: LibraryResponseData<T>,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class LibraryResponseData<T>(
    val next: String? = null,
    val `data`: List<T>? = null,
)