package com.snowdango.bijouk.model.cider.mapper

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.event.PlayBackTimeDidChangeEvent
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import java.text.SimpleDateFormat
import java.util.Date


@SuppressLint("SimpleDateFormat")
fun PlayBackTimeDidChangeEvent.convert(): PlayBackTimeData {
    val format = SimpleDateFormat("mm:ss")
    return PlayBackTimeData(
        duration = data.currentPlaybackDuration.toFloat(),
        currentTime = data.currentPlaybackTime.toFloat(),
        remainingTime = data.currentPlaybackTimeRemaining.toFloat(),
        currentTimeString = format.format(Date((data.currentPlaybackTime * 1000).toLong())),
        remainingTimeString = format.format(Date((data.currentPlaybackTimeRemaining * 1000).toLong())),
        isPlaying = data.isPlaying,
    )
}