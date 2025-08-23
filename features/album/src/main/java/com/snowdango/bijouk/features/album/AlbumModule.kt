package com.snowdango.bijouk.features.album

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object AlbumModule {

    val module = module {
        viewModel { param ->
            AlbumDetailViewModel(
                param.get(),
                param.get(),
                param.get(),
                param.get()
            )
        }
    }
}
