package com.snowdango.bijouk.model.cider

import com.snowdango.bijouk.domain.Logger
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.data.QueueDataList
import com.snowdango.bijouk.model.cider.mapper.api.convert
import com.snowdango.bijouk.repository.cider.CiderRPCRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class CiderRPCModel(
    baseUrl: String,
    token: String,
) : KoinComponent {

    private val logger = Logger("CiderRPCModel")

    private val repository: CiderRPCRepository by inject<CiderRPCRepository> {
        parametersOf(
            baseUrl,
            token
        )
    }

    suspend fun isActive(): Boolean {
        return try {
            val response = repository.getActive()
            response.status == "ok"
        } catch (_: Throwable) {
            false
        }
    }

    suspend fun getNowPlay(): Triple<NowPlayData, PlayBackTimeData, NowPlayingStatusData> {
        val nowPlay = repository.getNowPlay()
        return nowPlay.convert()
    }

    suspend fun getQueue(songId: String?): QueueDataList {
        val queues = repository.getQueue()
        val currentIndex = queues.indexOfLast { it.id == songId && it.playbackType != 0 }
        val isFinished = queues.none { it.playbackType == 0 }
        logger.d(
            "CiderRPCModel",
            "getQueue: songId=${songId} currentIndex=$currentIndex, isFinished=$isFinished"
        )
        return QueueDataList(
            list = queues.mapIndexed { index, data ->
                data.convert(
                    index,
                    currentIndex,
                    isFinished
                )
            },
        )
    }

    suspend fun playPause() {
        repository.postPlayPause()
    }

    suspend fun next() {
        repository.postNext()
    }

    suspend fun prev() {
        repository.postPrev()
    }

    suspend fun seekTo(to: Float) {
        repository.seekTo(to)
    }

    suspend fun clearQueue() {
        repository.clearQueue()
    }

    suspend fun moveQueue(index: Int, moveIndex: Int) {
        repository.postMoveQueue(index, moveIndex)
    }

    suspend fun changeQueueIndex(index: Int) {
        repository.changeQueueIndex(index)
    }

    suspend fun playSongById(id: String) {
        repository.playSongById(id)
    }

    suspend fun playAlbumById(id: String) {
        repository.playAlbumById(id)
    }

    suspend fun playPlaylistById(id: String) {
        repository.playPlaylistById(id)
    }

    suspend fun playStationById(stationId: String) {
        repository.playStationById(stationId)
    }

    suspend fun playNextSongById(id: String) {
        repository.playNextSongById(id)
    }

    suspend fun playNextAlbumById(id: String) {
        repository.playNextAlbumById(id)
    }

    suspend fun playNextPlaylistById(id: String) {
        repository.playNextPlaylistById(id)
    }

    suspend fun playLaterSongById(id: String) {
        repository.playLaterSongById(id)
    }

    suspend fun playLaterAlbumById(id: String) {
        repository.playLaterAlbumById(id)
    }

    suspend fun playLaterPlaylistById(id: String) {
        repository.playLaterPlaylistById(id)
    }

    suspend fun playPlaylistFolderById(ids: List<String>) {
        ids.forEachIndexed { index, playlistId ->
            repository.playNextPlaylistById(playlistId)
        }
    }

    suspend fun getShuffleMode(): Boolean {
        val response = repository.getShuffleMode()
        return response.value == 1
    }

    suspend fun toggleShuffleMode() {
        repository.toggleShuffleMode()
    }

    suspend fun setShuffleMode(shuffle: Boolean, currentMode: Boolean) {
        if (currentMode == shuffle) {
            repeat(2) {
                toggleShuffleMode()
            }
        } else {
            toggleShuffleMode()
        }
    }

}