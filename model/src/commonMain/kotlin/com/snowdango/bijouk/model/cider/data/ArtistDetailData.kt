package com.snowdango.bijouk.model.cider.data

import com.snowdango.bijouk.model.cider.data.entity.Album
import com.snowdango.bijouk.model.cider.data.entity.Song

data class ArtistDetailData(
    val id: String,
    val href: String,
    val genres: List<String>,
    val name: String,
    val artwork: String,
    val url: String,
    val topSongs: List<Song>? = null,
    val fullAlbums: List<Album>? = null,
    val singles: List<Album>? = null,
)