package com.snowdango.bijouk.model.cider

import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class CiderBridgeModel(
    baseUrl: String,
    token: String,
) : KoinComponent {

    private val ciderModel: CiderModel by inject<CiderModel> {
        parametersOf(
            baseUrl,
            token
        )
    }

    private val ciderRPCModel: CiderRPCModel by inject<CiderRPCModel> {
        parametersOf(
            baseUrl,
            token
        )
    }

    suspend fun playPlaylistByIdShuffled(
        playlistId: String,
    ) {
        val shuffleState = ciderRPCModel.getShuffleMode()
        ciderRPCModel.clearQueue()
        ciderRPCModel.playNextPlaylistById(playlistId)
        @Suppress("MagicNumber")
        delay(1_500)
        ciderRPCModel.setShuffleMode(true, shuffleState)
        ciderRPCModel.next()
    }

    suspend fun playPlaylistFolderById(
        folderId: String,
    ) {
        val playlists = ciderModel.getAllPlaylistFolderChildren(folderId)
        if (playlists.isNotEmpty()) {
            ciderRPCModel.clearQueue()
            ciderRPCModel.playPlaylistFolderById(playlists.map { it.id }.reversed())
            ciderRPCModel.next()
        }
    }

    suspend fun playPlaylistFolderByIdShuffled(
        folderId: String,
    ) {
        val shuffleState = ciderRPCModel.getShuffleMode()
        val playlists = ciderModel.getAllPlaylistFolderChildren(folderId)
        if (playlists.isNotEmpty()) {
            ciderRPCModel.clearQueue()
            ciderRPCModel.playPlaylistFolderById(playlists.map { it.id })
            @Suppress("MagicNumber")
            delay(1_500)
            ciderRPCModel.setShuffleMode(true, shuffleState)
            ciderRPCModel.next()
        }
    }

}