package com.snowdango.bijouk.model.cider.data

data class AlbumDetailData(
    val id: String,
    val title: String,
    val artist: String,
    val artwork: String?,
    val genres: List<String>,
    val releaseDate: String,
    val trackCount: Int,
)