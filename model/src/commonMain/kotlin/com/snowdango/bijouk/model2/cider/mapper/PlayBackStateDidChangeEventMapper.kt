package com.snowdango.bijouk.model2.cider.mapper

import com.snowdango.bijouk.domain.api.event.Attributes
import com.snowdango.bijouk.domain.api.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.model2.cider.data.NowPlayData
import com.snowdango.bijouk.model2.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model2.cider.mapper.converter.PlaybackTimeMapper
import com.snowdango.bijouk.model2.cider.mapper.converter.convert

fun PlayBackStateDidChangeEvent.convert(): Pair<NowPlayData?, PlayBackTimeData?> {
    return Pair(
        data.attributes?.convertNowPlayData(),
        data.attributes?.convertPlayBackTimeData(),
    )
}

fun Attributes.convertNowPlayData(): NowPlayData {
    return NowPlayData(
        id = playParams.id,
        name = name,
        artistName = artistName,
        albumName = albumName,
        artwork = artwork.convert(),
        hasLyrics = hasLyrics,
    )
}

@Suppress("SimpleDateFormat")
fun Attributes.convertPlayBackTimeData(): PlayBackTimeData {
    return PlayBackTimeData(
        duration = durationInMillis / 1000f,
        currentTime = currentPlaybackTime.toFloat(),
        remainingTime = remainingTime.toFloat(),
        currentTimeString = PlaybackTimeMapper.convertPlaybackTime(currentPlaybackTime),
        remainingTimeString = PlaybackTimeMapper.convertRemainingTime(remainingTime),
        isPlaying = false,
    )
}
