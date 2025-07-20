package com.snowdango.bijouk.features.nowPlay

import com.snowdango.bijouk.features.nowPlay.view.nowplay.NowPlayingViewModel
import com.snowdango.bijouk.features.nowPlay.view.queue.QueueViewModel
import com.snowdango.bijouk.features.nowPlay.view.search.album.SearchAlbumsViewModel
import com.snowdango.bijouk.features.nowPlay.view.search.songs.SearchSongsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object NowPlayModule {

    val module = module {
        viewModel { param -> NowPlayViewModel(param.get(), param.get()) }
        viewModel { param -> NowPlayingViewModel(param.get(), param.get()) }
        viewModel { param -> QueueViewModel(param.get(), param.get()) }
        viewModel { param -> SearchSongsViewModel(param.get(), param.get()) }
        viewModel { param -> SearchAlbumsViewModel(param.get(), param.get()) }
    }
}
