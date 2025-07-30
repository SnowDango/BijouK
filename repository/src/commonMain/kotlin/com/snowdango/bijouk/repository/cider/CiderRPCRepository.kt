package com.snowdango.bijouk.repository.cider

import com.snowdango.bijouk.domain.api.CiderRPCApi
import com.snowdango.bijouk.domain.api.request.PlayRequestBody
import com.snowdango.bijouk.domain.api.response.BasicResponse
import com.snowdango.bijouk.domain.api.response.NowPlayingResponse
import com.snowdango.bijouk.domain.api.response.data.QueueResponseData

class CiderRPCRepository(
    private val ciderRPCApi: CiderRPCApi,
) {

    suspend fun getActive(): BasicResponse {
        return ciderRPCApi.active()
    }

    suspend fun getNowPlay(): NowPlayingResponse {
        return ciderRPCApi.nowPlaying()
    }

    suspend fun getQueue(): List<QueueResponseData> {
        return ciderRPCApi.getQueue()
    }

    suspend fun postPlayPause(): BasicResponse {
        return ciderRPCApi.playPause()
    }

    suspend fun postNext(): BasicResponse {
        return ciderRPCApi.next()
    }

    suspend fun postPrev(): BasicResponse {
        return ciderRPCApi.previous()
    }

    suspend fun seekTo(to: Float): BasicResponse {
        return ciderRPCApi.seekTo(to)
    }

    suspend fun postMoveQueue(index: Int, moveIndex: Int): BasicResponse {
        return ciderRPCApi.moveQueue(index, moveIndex)
    }

    suspend fun changeQueueIndex(index: Int): BasicResponse {
        return ciderRPCApi.changeQueueIndex(index)
    }

    suspend fun songPlayById(id: String): BasicResponse {
        return ciderRPCApi.playById(id, PlayRequestBody.PlayType.Songs)
    }

    suspend fun albumPlayById(id: String): BasicResponse {
        return ciderRPCApi.playById(id, PlayRequestBody.PlayType.Albums)
    }

    suspend fun playlistPlayById(id: String): BasicResponse {
        return ciderRPCApi.playById(id, PlayRequestBody.PlayType.Playlists)
    }

    suspend fun songPlayNextById(id: String): BasicResponse {
        return ciderRPCApi.playNextById(id, PlayRequestBody.PlayType.Songs)
    }

    suspend fun albumPlayNextById(id: String): BasicResponse {
        return ciderRPCApi.playNextById(id, PlayRequestBody.PlayType.Albums)
    }

    suspend fun playlistPlayNextById(id: String): BasicResponse {
        return ciderRPCApi.playNextById(id, PlayRequestBody.PlayType.Playlists)
    }

    suspend fun songPlayLaterById(id: String): BasicResponse {
        return ciderRPCApi.playLaterById(id, PlayRequestBody.PlayType.Songs)
    }

    suspend fun albumPlayLaterById(id: String): BasicResponse {
        return ciderRPCApi.playLaterById(id, PlayRequestBody.PlayType.Albums)
    }

    suspend fun playlistPlayLaterById(id: String): BasicResponse {
        return ciderRPCApi.playLaterById(id, PlayRequestBody.PlayType.Playlists)
    }

    suspend fun stationPlayById(stationId: String): BasicResponse {
        return ciderRPCApi.playById(stationId, PlayRequestBody.PlayType.Stations)
    }

}