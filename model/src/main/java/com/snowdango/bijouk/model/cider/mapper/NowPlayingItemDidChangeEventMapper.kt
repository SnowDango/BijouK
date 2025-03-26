package com.snowdango.bijouk.model.cider.mapper

import com.snowdango.bijouk.domain.api.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.model.cider.data.NowPlayData

fun NowPlayingItemDidChangeEvent.convert(): NowPlayData {
    return NowPlayData(
        name = data.name,
        artistName = data.artistName,
        albumName = data.albumName,
        artwork = data.artwork.url
            .replace("{w}", data.artwork.width?.toString() ?: "1000")
            .replace("{h}", data.artwork.height?.toString() ?: "1000"),
        hasLyrics = data.hasLyrics,
    )
}
