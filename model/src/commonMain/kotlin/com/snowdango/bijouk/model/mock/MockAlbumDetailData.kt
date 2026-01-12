package com.snowdango.bijouk.model.mock

import com.snowdango.bijouk.model.cider.data.AlbumDetailData


val mockAlbumDetailData = AlbumDetailData(
    id = "mock_id",
    title = "モックアルバム",
    artist = "モックアーティスト",
    artwork = "mock_artwork_url",
    releaseDate = "2024-01-01",
    genres = listOf(),
    trackCount = 8,
)