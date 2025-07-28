package com.snowdango.bijouk.domain.api.response.data

import com.snowdango.bijouk.domain.api.entity.Albums
import com.snowdango.bijouk.domain.api.entity.Artists
import com.snowdango.bijouk.domain.api.entity.Playlists
import com.snowdango.bijouk.domain.api.entity.Songs
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchInLibraryResponseData(
    val meta: SearchInLibraryResponseMeta,
    val results: SearchInLibraryResponseResults,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchInLibraryResponseMeta(
    val metrics: SearchInLibraryResponseMetaMetrics? = null,
    val results: SearchInLibraryResponseMetaResults,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchInLibraryResponseMetaMetrics(
    val dataSetId: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchInLibraryResponseMetaResults(
    val order: List<String>,
    val rawOrder: List<String>? = null,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class SearchInLibraryResponseResults(
    @SerialName("library-albums")
    val albums: Albums? = null,
    @SerialName("library-artists")
    val artists: Artists? = null,
    @SerialName("library-playlists")
    val playlists: Playlists? = null,
    @SerialName("library-songs")
    val songs: Songs? = null,
)
