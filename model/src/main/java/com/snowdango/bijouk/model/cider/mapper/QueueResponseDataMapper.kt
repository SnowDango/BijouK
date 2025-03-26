package com.snowdango.bijouk.model.cider.mapper

import com.snowdango.bijouk.domain.api.response.data.QueueAttributes
import com.snowdango.bijouk.domain.api.response.data.QueueResponseData
import com.snowdango.bijouk.model.cider.data.QueueData


fun QueueResponseData.convert(): QueueData {
    return attributes.convert(id, playbackType)
}

fun QueueAttributes.convert(songId: String, playbackType: Int): QueueData {
    return QueueData(
        songId = songId,
        name = name,
        artist = artistName,
        album = albumName,
        artwork = artwork?.let { art ->
            art.url.replace("{w}", art.width?.toString() ?: "1000")
                .replace("{h}", art.height?.toString() ?: "1000")
        } ?: "",
        playbackType = playbackType,
    )
}