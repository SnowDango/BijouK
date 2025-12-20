package com.snowdango.bijouk.repository.cider

import com.snowdango.bijouk.api.CiderRpcApi
import com.snowdango.bijouk.api.model.BasicResponse
import com.snowdango.bijouk.api.model.NowPlayingResponse
import com.snowdango.bijouk.api.model.PlayRequestBody
import com.snowdango.bijouk.api.model.PlaybackQueueChangeToIndexPostRequest
import com.snowdango.bijouk.api.model.PlaybackQueueMoveToPositionPostRequest
import com.snowdango.bijouk.api.model.PlaybackSeekPostRequest
import com.snowdango.bijouk.api.model.PlaybackShuffleModeGet200Response
import com.snowdango.bijouk.api.model.QueueResponseData

class CiderRPCRepository(
    private val ciderRpcApi: CiderRpcApi,
) {

    suspend fun getActive(): BasicResponse {
        return ciderRpcApi.playbackActiveGet().body()
    }

    suspend fun getNowPlay(): NowPlayingResponse {
        return ciderRpcApi.playbackNowPlayingGet().body()
    }

    suspend fun getQueue(): List<QueueResponseData> {
        return ciderRpcApi.playbackQueueGet().body()
    }

    suspend fun postPlayPause(): BasicResponse {
        return ciderRpcApi.playbackPlaypausePost().body()
    }

    suspend fun postNext(): BasicResponse {
        return ciderRpcApi.playbackNextPost().body()
    }

    suspend fun postPrev(): BasicResponse {
        return ciderRpcApi.playbackPreviousPost().body()
    }

    suspend fun seekTo(to: Float): BasicResponse {
        return ciderRpcApi.playbackSeekPost(
            PlaybackSeekPostRequest(
                position = to,
            )
        ).body()
    }

    suspend fun clearQueue(): BasicResponse {
        return ciderRpcApi.playbackQueueClearQueuePost().body()
    }

    suspend fun postMoveQueue(index: Int, moveIndex: Int): BasicResponse {
        return ciderRpcApi.playbackQueueMoveToPositionPost(
            PlaybackQueueMoveToPositionPostRequest(
                startIndex = index,
                destinationIndex = moveIndex,
            )
        ).body()
    }

    suspend fun changeQueueIndex(index: Int): BasicResponse {
        return ciderRpcApi.playbackQueueChangeToIndexPost(
            PlaybackQueueChangeToIndexPostRequest(index = index)
        ).body()
    }

    suspend fun playSongById(id: String): BasicResponse {
        return ciderRpcApi.playbackPlayItemPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.SONGS,
                id = id,
            )
        ).body()
    }

    suspend fun playAlbumById(id: String): BasicResponse {
        return ciderRpcApi.playbackPlayItemPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.ALBUMS,
                id = id,
            )
        ).body()
    }

    suspend fun playPlaylistById(id: String): BasicResponse {
        return ciderRpcApi.playbackPlayItemPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.PLAYLISTS,
                id = id,
            )
        ).body()
    }

    suspend fun playNextSongById(id: String): BasicResponse {
        return ciderRpcApi.playbackPlayNextPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.SONGS,
                id = id,
            )
        ).body()
    }

    suspend fun playNextAlbumById(id: String): BasicResponse {
        return ciderRpcApi.playbackPlayNextPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.ALBUMS,
                id = id,
            )
        ).body()
    }

    suspend fun playNextPlaylistById(id: String): BasicResponse {
        return ciderRpcApi.playbackPlayNextPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.PLAYLISTS,
                id = id,
            )
        ).body()
    }

    suspend fun playLaterSongById(id: String): BasicResponse {
        return ciderRpcApi.playbackPlayLaterPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.SONGS,
                id = id,
            )
        ).body()
    }

    suspend fun playLaterAlbumById(id: String): BasicResponse {
        return ciderRpcApi.playbackPlayLaterPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.ALBUMS,
                id = id,
            )
        ).body()
    }

    suspend fun playLaterPlaylistById(id: String): BasicResponse {
        return ciderRpcApi.playbackPlayLaterPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.PLAYLISTS,
                id = id,
            )
        ).body()
    }

    suspend fun playStationById(stationId: String): BasicResponse {
        return ciderRpcApi.playbackPlayItemPost(
            PlayRequestBody(
                type = PlayRequestBody.Type.STATIONS,
                id = stationId,
            )
        ).body()
    }

    suspend fun getShuffleMode(): PlaybackShuffleModeGet200Response {
        return ciderRpcApi.playbackShuffleModeGet().body()
    }

    suspend fun toggleShuffleMode(): BasicResponse {
        return ciderRpcApi.playbackToggleShufflePost().body()
    }

}