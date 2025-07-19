package com.snowdango.bijouk.model2.cider.mapper

import com.snowdango.bijouk.domain.api.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.model2.cider.data.NowPlayData
import com.snowdango.bijouk.model2.cider.mapper.converter.convert

fun NowPlayingItemDidChangeEvent.convert(): NowPlayData {
    return NowPlayData(
        id = data.playParams.id,
        name = data.name,
        artistName = data.artistName,
        albumName = data.albumName,
        artwork = data.artwork.convert(),
        hasLyrics = data.hasLyrics,
    )
}
