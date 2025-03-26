package com.snowdango.bijouk.features.now_play

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object NowPlayModule {

    val module = module {
        viewModel { param -> NowPlayViewModel(param.get(), param.get(), param.get(), param.get()) }
    }
}
