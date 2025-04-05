package com.snowdango.bijouk.model.cider.mapper

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.event.PlayBackTimeDidChangeEvent
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.mapper.converter.convertPlaybackTime
import com.snowdango.bijouk.model.cider.mapper.converter.convertRemainingTime

@SuppressLint("SimpleDateFormat")
fun PlayBackTimeDidChangeEvent.convert(): PlayBackTimeData {
    return PlayBackTimeData(
        duration = data.currentPlaybackDuration.toFloat(),
        currentTime = data.currentPlaybackTime.toFloat(),
        remainingTime = data.currentPlaybackTimeRemaining.toFloat(),
        currentTimeString = data.currentPlaybackTime.convertPlaybackTime(),
        remainingTimeString = data.currentPlaybackTimeRemaining.convertRemainingTime(),
        isPlaying = data.isPlaying,
    )
}
