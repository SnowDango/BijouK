package com.snowdango.bijouk.repository.cider

import com.snowdango.bijouk.domain.api.rpc.CiderRPCApi
import com.snowdango.bijouk.domain.api.rpc.request.PlayRequestBody
import com.snowdango.bijouk.domain.api.rpc.response.BasicResponse
import com.snowdango.bijouk.domain.api.rpc.response.NowPlayingResponse
import com.snowdango.bijouk.domain.api.rpc.response.QueueResponseData
import com.snowdango.bijouk.domain.api.rpc.response.ShuffleResponse

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

    suspend fun playSongById(id: String): BasicResponse {
        return ciderRPCApi.playById(id, PlayRequestBody.PlayType.Songs)
    }

    suspend fun playAlbumById(id: String): BasicResponse {
        return ciderRPCApi.playById(id, PlayRequestBody.PlayType.Albums)
    }

    suspend fun playPlaylistById(id: String): BasicResponse {
        return ciderRPCApi.playById(id, PlayRequestBody.PlayType.Playlists)
    }

    suspend fun playNextSongById(id: String): BasicResponse {
        return ciderRPCApi.playNextById(id, PlayRequestBody.PlayType.Songs)
    }

    suspend fun playNextAlbumById(id: String): BasicResponse {
        return ciderRPCApi.playNextById(id, PlayRequestBody.PlayType.Albums)
    }

    suspend fun playNextPlaylistById(id: String): BasicResponse {
        return ciderRPCApi.playNextById(id, PlayRequestBody.PlayType.Playlists)
    }

    suspend fun playLaterSongById(id: String): BasicResponse {
        return ciderRPCApi.playLaterById(id, PlayRequestBody.PlayType.Songs)
    }

    suspend fun playLaterAlbumById(id: String): BasicResponse {
        return ciderRPCApi.playLaterById(id, PlayRequestBody.PlayType.Albums)
    }

    suspend fun playLaterPlaylistById(id: String): BasicResponse {
        return ciderRPCApi.playLaterById(id, PlayRequestBody.PlayType.Playlists)
    }

    suspend fun playStationById(stationId: String): BasicResponse {
        return ciderRPCApi.playById(stationId, PlayRequestBody.PlayType.Stations)
    }

    suspend fun getShuffleMode(): ShuffleResponse {
        return ciderRPCApi.getShuffleMode()
    }

    suspend fun toggleShuffleMode(): BasicResponse {
        return ciderRPCApi.toggleShuffleMode()
    }

}