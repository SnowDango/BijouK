package com.snowdango.bijouk.model.cider.mapper

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.entity.PlayBackData
import com.snowdango.bijouk.domain.api.response.NowPlayingResponse
import com.snowdango.bijouk.domain.api.response.data.Info
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import java.text.SimpleDateFormat
import java.util.Date


fun NowPlayingResponse.convert(): Triple<NowPlayData, PlayBackTimeData, NowPlayingStatusData> {
    return Triple(
        info.convertNowPlayData(),
        info.convertPlayBackTimeData(),
        info.convertNowPlayingStatusData(),
    )
}

fun Info.convertNowPlayData(): NowPlayData {
    return NowPlayData(
        name = name,
        artistName = artistName,
        albumName = albumName,
        artwork = artwork.url
            .replace("{w}", artwork.width?.toString() ?: "1000")
            .replace("{h}", artwork.height?.toString() ?: "1000"),
        hasLyrics = hasLyrics,
    )
}

@SuppressLint("SimpleDateFormat")
fun Info.convertPlayBackTimeData(): PlayBackTimeData {
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

fun Info.convertNowPlayingStatusData(): NowPlayingStatusData {
    return NowPlayingStatusData(
        isFav = inFavorites,
        isInLib = inLibrary,
    )
}