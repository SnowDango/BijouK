package com.snowdango.bijouk.domain.api.response

import com.snowdango.bijouk.domain.api.response.data.SearchInLibraryResponseData
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchInLibraryResponse(
    val `data`: SearchInLibraryResponseData,
)
