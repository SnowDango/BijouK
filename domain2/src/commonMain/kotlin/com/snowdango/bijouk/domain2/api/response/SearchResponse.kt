package com.snowdango.bijouk.domain2.api.response

import com.snowdango.bijouk.domain2.api.response.data.SearchResponseData
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchResponse(
    val `data`: SearchResponseData
)
