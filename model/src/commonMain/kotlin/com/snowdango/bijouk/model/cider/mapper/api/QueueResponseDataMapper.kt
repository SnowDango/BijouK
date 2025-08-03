package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.rpc.response.QueueAttributes
import com.snowdango.bijouk.domain.api.rpc.response.QueueResponseData
import com.snowdango.bijouk.model.cider.data.QueueData
import com.snowdango.bijouk.model.cider.mapper.converter.convert

fun QueueResponseData.convert(index: Int, currentIndex: Int, isFinish: Boolean): QueueData {
    return attributes.convert(id, index, currentIndex, isFinish)
}

fun QueueAttributes.convert(
    songId: String,
    index: Int,
    currentIndex: Int,
    isFinish: Boolean
): QueueData {
    return QueueData(
        songId = songId,
        name = name,
        artist = artistName.orEmpty(),
        album = albumName,
        artwork = artwork?.convert().orEmpty(),
        state = if (isFinish) {
            QueueData.State.Before
        } else if (index < currentIndex) {
            QueueData.State.Before
        } else if (index == currentIndex) {
            QueueData.State.Current
        } else {
            QueueData.State.Waiting
        }
    )
}
