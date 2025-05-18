package com.snowdango.bijouk.domain

import com.snowdango.bijouk.domain.api.CiderApi
import com.snowdango.bijouk.domain.api.CiderSocket
import org.koin.dsl.module

object DomainModule {
    val module = module {
        // single<DevicesDatabase> { DevicesDatabase.getDatabase(get()) }
        factory<CiderApi> { param -> CiderApi(baseUrl = param.get(), token = param.get()) }
        factory<CiderSocket> { param -> CiderSocket(baseUrl = param.get()) }
    }
}
