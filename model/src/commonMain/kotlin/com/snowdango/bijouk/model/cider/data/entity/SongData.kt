package com.snowdango.bijouk.model.cider.data.entity

data class SongData(
    val id: String,
    val name: String,
    val album: String,
    val artist: String,
    val artwork: String,
    val href: String,
    val genres: List<String>? = null,
    val hasLyrics: Boolean,
    val composerName: String? = null,
    val catalog: SongData?,
)
