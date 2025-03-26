package com.snowdango.bijouk.model.cider.data

data class QueueDataList(
    val list: List<QueueData>,
)

data class QueueData(
    val songId: String,
    val artwork: String,
    val name: String,
    val artist: String,
    val album: String,
    val playbackType: Int,
)
