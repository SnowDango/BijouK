package com.snowdango.bijouk.model.cider.data

data class SearchData(
    val playlists: List<SearchPlaylist>?,
    val songs: List<SearchSong>?,
    val albums: List<SearchAlbum>?,
    val artists: List<SearchArtist>?,
)

data class SearchPlaylist(
    val id: String,
    val name: String,
    val curatorName: String,
    val description: String?,
    val artwork: String?,
    val href: String,
)

data class SearchSong(
    val id: String,
    val name: String,
    val album: String,
    val artist: String,
    val artwork: String,
    val href: String,
)

data class SearchAlbum(
    val id: String,
    val name: String,
    val artist: String,
    val artwork: String,
    val href: String,
)

data class SearchArtist(
    val id: String,
    val name: String,
    val artwork: String,
    val href: String,
    val albums: List<SearchAlbum>,
)
