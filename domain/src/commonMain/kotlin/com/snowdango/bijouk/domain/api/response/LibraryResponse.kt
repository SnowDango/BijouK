package com.snowdango.bijouk.domain.api.response

import com.snowdango.bijouk.domain.api.response.data.LibraryResponseData
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class LibraryResponse<T>(
    val `data`: LibraryResponseData<T>,
)
