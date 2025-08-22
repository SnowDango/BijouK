package com.snowdango.bijouk.model.cider.data.entity

data class ArtistData(
    val id: String,
    val name: String,
    val artwork: String,
    val href: String,
    val catalog: ArtistData?,
)
