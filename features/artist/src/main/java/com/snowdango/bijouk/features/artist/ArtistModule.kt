package com.snowdango.bijouk.features.artist

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object ArtistModule {

    val module = module {
        viewModel { param -> ArtistsDetailViewModel(param.get(), param.get(), param.get()) }
    }
}