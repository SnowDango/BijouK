package com.snowdango.bijouk.infla

import org.koin.dsl.module

object InflaModule {

    val module = module {
        single { SharedEventStore(get()) }
    }

}