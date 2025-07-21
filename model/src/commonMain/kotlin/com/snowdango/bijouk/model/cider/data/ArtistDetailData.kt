package com.snowdango.bijouk.model.cider.data

data class ArtistDetailData(
    val id: String,
    val href: String,
    val genres: List<String>,
    val name: String,
    val artwork: String,
    val url: String,
)