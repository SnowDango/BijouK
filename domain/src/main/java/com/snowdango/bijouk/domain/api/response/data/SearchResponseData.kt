package com.snowdango.bijouk.domain.api.response.data

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.entity.Albums
import com.snowdango.bijouk.domain.api.entity.Artists
import com.snowdango.bijouk.domain.api.entity.Playlists
import com.snowdango.bijouk.domain.api.entity.Songs
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchResponseData(
    val meta: SearchResponseMeta,
    val results: SearchResponseResults,
)


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchResponseMeta(
    val metrics: SearchResponseMetaMetrics,
    val results: SearchResponseMetaResults,
)


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchResponseMetaMetrics(
    val dataSetId: String
)


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchResponseMetaResults(
    val order: List<String>,
    val rawOrder: List<String>
)


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SearchResponseResults(
    val albums: Albums? = null,
    val artists: Artists? = null,
    val playlists: Playlists? = null,
    val songs: Songs? = null,
)