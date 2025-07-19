package com.snowdango.bijouk.model2.cider.mapper.converter

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

actual object PlaybackTimeMapper {

    @SuppressLint("SimpleDateFormat")
    actual fun convertPlaybackTime(playbackTime: Double): String {
        val format = SimpleDateFormat("mm:ss")
        return format.format(Date((playbackTime * 1_000).toLong()))
    }

    @SuppressLint("SimpleDateFormat")
    actual fun convertRemainingTime(remainingTime: Double): String {
        val format = SimpleDateFormat("mm:ss")
        return format.format(Date((remainingTime * 1_000).toLong()))
    }

}