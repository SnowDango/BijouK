package com.snowdango.bijouk.model2.cider.mapper

import com.snowdango.bijouk.domain.api.event.PlayBackTimeDidChangeEvent
import com.snowdango.bijouk.model2.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model2.cider.mapper.converter.PlaybackTimeMapper

@Suppress("SimpleDateFormat")
fun PlayBackTimeDidChangeEvent.convert(): PlayBackTimeData {
    return PlayBackTimeData(
        duration = data.currentPlaybackDuration.toFloat(),
        currentTime = data.currentPlaybackTime.toFloat(),
        remainingTime = data.currentPlaybackTimeRemaining.toFloat(),
        currentTimeString = PlaybackTimeMapper.convertPlaybackTime(data.currentPlaybackTime),
        remainingTimeString = PlaybackTimeMapper.convertRemainingTime(data.currentPlaybackTimeRemaining),
        isPlaying = data.isPlaying,
    )
}
