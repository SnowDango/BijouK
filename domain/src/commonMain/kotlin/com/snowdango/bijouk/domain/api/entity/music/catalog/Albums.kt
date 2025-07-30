package com.snowdango.bijouk.domain.api.entity.music.catalog

import com.snowdango.bijouk.domain.api.entity.music.PlayParameters
import kotlinx.serialization.Serializable

@Serializable
data class Albums(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes,
    val relationships: Relationships? = null,
    val views: Views? = null,
) {
    @Serializable
    data class Attributes(
        val artistName: String,
        val artistUrl: String? = null,
        val artwork: Artwork,
        val contentRating: String? = null,
        val copyright: String? = null,
        val editorialVideo: EditorialNotes? = null,
        val genreNames: List<String>,
        val inFavorites: Boolean? = null,
        val isCompilation: Boolean,
        val isComplete: Boolean,
        val isMasteredForItunes: Boolean,
        val isSingle: Boolean,
        val name: String,
        val playParams: PlayParameters? = null,
        val recordLabel: String? = null,
        val releaseDate: String? = null,
        val trackCount: Int,
        val upc: String? = null,
        val url: String,
    )

    @Serializable
    data object Relationships // 今のところ使う予定ないので作成していない

    @Serializable
    data object Views // 今のところ使う予定ないので作成していない
}

