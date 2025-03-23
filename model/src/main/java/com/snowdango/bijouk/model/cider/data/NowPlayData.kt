package com.snowdango.bijouk.model.cider.data

data class NowPlayData(
    val name: String,
    val artistName: String,
    val albumName: String,
    val artwork: String,
    val hasLyrics: Boolean,
)