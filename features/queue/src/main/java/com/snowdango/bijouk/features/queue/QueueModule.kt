package com.snowdango.bijouk.features.queue

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val queueModule = module {
    viewModel { param -> QueueViewModel(param.get(), param.get()) }
}

