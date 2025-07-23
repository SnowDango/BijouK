package com.snowdango.bijouk.features.queue

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object QueueModule {

    val module = module {
        viewModel { param -> QueueViewModel(param.get(), param.get()) }
    }
}
