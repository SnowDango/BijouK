package com.snowdango.bijouk.domain2.api.response.data

import com.snowdango.bijouk.domain2.api.entity.Albums
import com.snowdango.bijouk.domain2.api.entity.Artists
import com.snowdango.bijouk.domain2.api.entity.Playlists
import com.snowdango.bijouk.domain2.api.entity.Songs
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchResponseData(
    val meta: SearchResponseMeta,
    val results: SearchResponseResults,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchResponseMeta(
    val metrics: SearchResponseMetaMetrics,
    val results: SearchResponseMetaResults,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchResponseMetaMetrics(
    val dataSetId: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchResponseMetaResults(
    val order: List<String>,
    val rawOrder: List<String>
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchResponseResults(
    val albums: Albums? = null,
    val artists: Artists? = null,
    val playlists: Playlists? = null,
    val songs: Songs? = null,
)
