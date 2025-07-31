package com.snowdango.bijouk.model.cider.mapper.event

import com.snowdango.bijouk.domain.api.socket.event.Attributes
import com.snowdango.bijouk.domain.api.socket.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.mapper.converter.PlaybackTimeMapper
import com.snowdango.bijouk.model.cider.mapper.converter.convert

fun PlayBackStateDidChangeEvent.convert(): Pair<NowPlayData?, PlayBackTimeData?> {
    return Pair(
        data.attributes?.convertNowPlayData(),
        data.attributes?.convertPlayBackTimeData(),
    )
}

fun Attributes.convertNowPlayData(): NowPlayData {
    return NowPlayData(
        id = playParams?.id.orEmpty(),
        name = name,
        artistName = artistName,
        albumName = albumName,
        artwork = artwork?.convert().orEmpty(),
        hasLyrics = hasLyrics,
    )
}

@Suppress("SimpleDateFormat")
fun Attributes.convertPlayBackTimeData(): PlayBackTimeData {
    return PlayBackTimeData(
        duration = durationInMillis / 1000f,
        currentTime = currentPlaybackTime?.toFloat() ?: 0f,
        remainingTime = remainingTime?.toFloat() ?: 0f,
        currentTimeString = currentPlaybackTime?.let { PlaybackTimeMapper.convertPlaybackTime(it) }
            ?: "00:00",
        remainingTimeString = remainingTime?.let { PlaybackTimeMapper.convertRemainingTime(it) }
            ?: "00:00",
        isPlaying = false,
    )
}
