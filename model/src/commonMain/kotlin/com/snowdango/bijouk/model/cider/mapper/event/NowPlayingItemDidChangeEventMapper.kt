package com.snowdango.bijouk.model.cider.mapper.event

import com.snowdango.bijouk.domain.api.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.mapper.converter.convert

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
