package com.snowdango.bijouk.model.cider.data.entity

data class Album(
    val id: String,
    val name: String,
    val artist: String,
    val artwork: String,
    val href: String,
    val genres: List<String>? = null,
    val copyRight: String? = null,
    val trackCount: Int,
)
