package com.snowdango.bijouk.model.cider.mapper

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.event.Attributes
import com.snowdango.bijouk.domain.api.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import java.text.SimpleDateFormat
import java.util.Date

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
        artwork = artwork.url
            .replace("{w}", artwork.width.toString())
            .replace("{h}", artwork.height.toString()),
        hasLyrics = hasLyrics,
    )
}

@SuppressLint("SimpleDateFormat")
fun Attributes.convertPlayBackTimeData(): PlayBackTimeData {
    val format = SimpleDateFormat("mm:ss")
    return PlayBackTimeData(
        duration = durationInMillis / 1000f,
        currentTime = currentPlaybackTime.toFloat(),
        remainingTime = remainingTime.toFloat(),
        currentTimeString = format.format(Date((currentPlaybackTime * 1000).toLong())),
        remainingTimeString = format.format(Date((remainingTime * 1000).toLong())),
        isPlaying = false,
    )
}
