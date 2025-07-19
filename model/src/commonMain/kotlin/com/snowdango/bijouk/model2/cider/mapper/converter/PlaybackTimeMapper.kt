package com.snowdango.bijouk.model.cider.mapper.converter


expect object PlaybackTimeMapper {

    fun convertPlaybackTime(playbackTime: Double): String

    fun convertRemainingTime(remainingTime: Double): String

}