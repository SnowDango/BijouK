package com.snowdango.bijouk.model.cider.mapper

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.event.Attributes
import com.snowdango.bijouk.domain.api.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.model.cider.mapper.converter.convertPlaybackTime
import com.snowdango.bijouk.model.cider.mapper.converter.convertRemainingTime

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

@SuppressLint("SimpleDateFormat")
fun Attributes.convertPlayBackTimeData(): PlayBackTimeData {
    return PlayBackTimeData(
        duration = durationInMillis / 1000f,
        currentTime = currentPlaybackTime.toFloat(),
        remainingTime = remainingTime.toFloat(),
        currentTimeString = currentPlaybackTime.convertPlaybackTime(),
        remainingTimeString = remainingTime.convertRemainingTime(),
        isPlaying = false,
    )
}
