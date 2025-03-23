package com.snowdango.bijouk.features.device

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object DevicesModule {

    val devicesModule = module {
        viewModelOf(::DevicesViewModel)
    }

}