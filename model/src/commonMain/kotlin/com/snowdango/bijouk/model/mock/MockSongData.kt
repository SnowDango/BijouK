package com.snowdango.bijouk.model.mock

import com.snowdango.bijouk.model.cider.data.entity.SongData

val mockSongData = SongData(
    id = "mock_id",
    name = "Mock Song",
    album = "Mock Album",
    artist = "Mock Artist",
    artwork = "mock_artwork_url",
    href = "mock_href_url",
    genres = listOf("Pop", "Rock"),
    hasLyrics = true,
    composerName = "Mock Composer",
    catalog = null,
)