package com.snowdango.bijouk.features.search

import com.snowdango.bijouk.features.search.album.SearchAlbumsViewModel
import com.snowdango.bijouk.features.search.songs.SearchSongsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object SearchModule {

    val module = module {
        viewModel { param -> SearchViewModel(param.get(), param.get()) }
        viewModel { param -> SearchSongsViewModel(param.get(), param.get()) }
        viewModel { param -> SearchAlbumsViewModel(param.get(), param.get()) }
    }
}
