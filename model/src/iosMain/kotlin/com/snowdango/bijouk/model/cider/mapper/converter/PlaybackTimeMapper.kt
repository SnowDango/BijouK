package com.snowdango.bijouk.model.cider.mapper.converter

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone

actual object PlaybackTimeMapper {

    actual fun convertPlaybackTime(playbackTime: Double): String {
        val formatter = NSDateFormatter().apply {
            dateFormat = "mm:ss"
            timeZone = NSTimeZone.localTimeZone
        }
        val date = NSDate(playbackTime / 1000)
        return formatter.stringFromDate(date)
    }


    actual fun convertRemainingTime(remainingTime: Double): String {
        val formatter = NSDateFormatter().apply {
            dateFormat = "mm:ss"
            timeZone = NSTimeZone.localTimeZone
        }
        val date = NSDate(remainingTime / 1000)
        return formatter.stringFromDate(date)
    }

}