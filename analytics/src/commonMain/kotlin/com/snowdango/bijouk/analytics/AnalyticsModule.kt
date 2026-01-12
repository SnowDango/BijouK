package com.snowdango.bijouk.analytics

import com.snowdango.bijouk.analytics.logger.Logger
import org.koin.dsl.module

object AnalyticsModule {

    val Module = module {
        single<Logger> { Logger() }
    }

}