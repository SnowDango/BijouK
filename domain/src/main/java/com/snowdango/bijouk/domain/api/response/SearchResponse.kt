package com.snowdango.bijouk.domain.api.response

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.response.data.SearchResponseData
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchResponse(
    val `data`: SearchResponseData
)
