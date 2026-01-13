package com.snowdango.bijouk.features.artist

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val artistModule = module {
    viewModel { param ->
        ArtistsDetailViewModel(
            param.get(),
            param.get(),
            param.get(),
            param.get()
        )
    }
}
