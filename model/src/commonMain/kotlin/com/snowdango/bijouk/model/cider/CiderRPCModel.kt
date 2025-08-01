package com.snowdango.bijouk.model.cider

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

    suspend fun getQueue(): QueueDataList {
        val queues = repository.getQueue()
        val currentIndex = queues.indexOfLast { it.attributes.currentPlaybackTime != null }
        return QueueDataList(
            list = queues.mapIndexed { index, data -> data.convert(index, currentIndex) },
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

    suspend fun moveQueue(index: Int, moveIndex: Int) {
        repository.postMoveQueue(index, moveIndex)
    }

    suspend fun changeQueueIndex(index: Int) {
        repository.changeQueueIndex(index)
    }

    suspend fun songPlayById(id: String) {
        repository.songPlayById(id)
    }

    suspend fun albumPlayById(id: String) {
        repository.albumPlayById(id)
    }

    suspend fun playlistPlayById(id: String) {
        repository.playlistPlayById(id)
    }

    suspend fun stationPlayById(stationId: String) {
        repository.stationPlayById(stationId)
    }

    suspend fun songPlayNextById(id: String) {
        repository.songPlayNextById(id)
    }

    suspend fun albumPlayNextById(id: String) {
        repository.albumPlayNextById(id)
    }

    suspend fun playlistPlayNextById(id: String) {
        repository.playlistPlayNextById(id)
    }

    suspend fun songPlayLaterById(id: String) {
        repository.songPlayLaterById(id)
    }

    suspend fun albumPlayLaterById(id: String) {
        repository.albumPlayLaterById(id)
    }

    suspend fun playlistPlayLaterById(id: String) {
        repository.playlistPlayLaterById(id)
    }

    suspend fun playlistFolderPlayById(ids: List<String>) {
        ids.forEachIndexed { index, playlistId ->
           repository.playlistPlayNextById(playlistId)
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
        }else {
            toggleShuffleMode()
        }
    }

}