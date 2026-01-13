package com.snowdango.bijouk.features.playlist

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val playlistModule = module {
    viewModel { param ->
        PlaylistViewModel(
            param.get(),
            param.get(),
            param.get(),
            param.get()
        )
    }
}

