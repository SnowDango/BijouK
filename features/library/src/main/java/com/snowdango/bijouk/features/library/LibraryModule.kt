package com.snowdango.bijouk.features.library

import com.snowdango.bijouk.features.library.album.LibraryAlbumsViewModel
import com.snowdango.bijouk.features.library.playlist.LibraryPlaylistViewModel
import com.snowdango.bijouk.features.library.songs.LibrarySongsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object LibraryModule {

    val module = module {
        viewModel { param -> LibraryViewModel(param.get(), param.get()) }
        viewModel { param -> LibrarySongsViewModel(param.get(), param.get()) }
        viewModel { param -> LibraryAlbumsViewModel(param.get(), param.get()) }
        viewModel { param -> LibraryPlaylistViewModel(param.get(), param.get()) }
    }
}