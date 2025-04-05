package com.snowdango.bijouk.model.cider.mapper.converter

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Double.convertPlaybackTime(): String {
    val format = SimpleDateFormat("mm:ss")
    return format.format(Date((this * 1_000).toLong()))
}

@SuppressLint("SimpleDateFormat")
fun Double.convertRemainingTime(): String {
    val format = SimpleDateFormat("mm:ss")
    return format.format(Date((this * 1_000).toLong()))
}
