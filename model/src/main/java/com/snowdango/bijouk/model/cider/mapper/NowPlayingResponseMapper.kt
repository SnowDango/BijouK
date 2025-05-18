package com.snowdango.bijouk.model.cider.mapper

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain2.api.response.NowPlayingResponse
import com.snowdango.bijouk.domain2.api.response.data.NowPlayingResponseData
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.model.cider.mapper.converter.convertPlaybackTime
import com.snowdango.bijouk.model.cider.mapper.converter.convertRemainingTime

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

@SuppressLint("SimpleDateFormat")
fun NowPlayingResponseData.convertPlayBackTimeData(): PlayBackTimeData {
    return PlayBackTimeData(
        duration = durationInMillis / 1000f,
        currentTime = currentPlaybackTime.toFloat(),
        remainingTime = remainingTime.toFloat(),
        currentTimeString = currentPlaybackTime.convertPlaybackTime(),
        remainingTimeString = remainingTime.convertRemainingTime(),
        isPlaying = false,
    )
}

fun NowPlayingResponseData.convertNowPlayingStatusData(): NowPlayingStatusData {
    return NowPlayingStatusData(
        isFav = inFavorites,
        isInLib = inLibrary,
    )
}
