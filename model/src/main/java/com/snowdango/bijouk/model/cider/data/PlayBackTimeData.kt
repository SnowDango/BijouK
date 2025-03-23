package com.snowdango.bijouk.model.cider.data


data class PlayBackTimeData(
    val duration: Float,
    val currentTime: Float,
    val remainingTime: Float,
    val currentTimeString: String,
    val remainingTimeString: String,
    val isPlaying: Boolean,
)
