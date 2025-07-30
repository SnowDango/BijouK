package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.rpc.response.NowPlayingResponse
import com.snowdango.bijouk.domain.api.rpc.response.NowPlayingResponseData
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.mapper.converter.PlaybackTimeMapper
import com.snowdango.bijouk.model.cider.mapper.converter.convert

fun NowPlayingResponse.convert(): Triple<NowPlayData, PlayBackTimeData, NowPlayingStatusData> {
    return Triple(
        info.convertNowPlayData(),
        info.convertPlayBackTimeData(),
        info.convertNowPlayingStatusData(),
    )
}

fun NowPlayingResponseData.convertNowPlayData(): NowPlayData {
    return NowPlayData(
        id = playParams?.id.toString(),
        name = name,
        artistName = artistName,
        albumName = albumName,
        artwork = artwork.convert(),
        hasLyrics = hasLyrics,
    )
}

@Suppress("SimpleDateFormat")
fun NowPlayingResponseData.convertPlayBackTimeData(): PlayBackTimeData {
    return PlayBackTimeData(
        duration = durationInMillis / 1000f,
        currentTime = currentPlaybackTime.toFloat(),
        remainingTime = remainingTime.toFloat(),
        currentTimeString = PlaybackTimeMapper.convertPlaybackTime(currentPlaybackTime),
        remainingTimeString = PlaybackTimeMapper.convertRemainingTime(remainingTime),
        isPlaying = false,
    )
}

fun NowPlayingResponseData.convertNowPlayingStatusData(): NowPlayingStatusData {
    return NowPlayingStatusData(
        isFav = inFavorites,
        isInLib = inLibrary,
    )
}
