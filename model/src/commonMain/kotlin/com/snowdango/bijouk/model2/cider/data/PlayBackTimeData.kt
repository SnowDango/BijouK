package com.snowdango.bijouk.model2.cider.data

data class PlayBackTimeData(
    val duration: Float,
    val currentTime: Float,
    val remainingTime: Float,
    val currentTimeString: String,
    val remainingTimeString: String,
    val isPlaying: Boolean,
)
