package com.snowdango.bijouk.domain

import com.snowdango.bijouk.domain.api.CiderApi
import com.snowdango.bijouk.domain.api.CiderSocket
import com.snowdango.bijouk.domain.db.DevicesDatabase
import com.snowdango.bijouk.domain.db.getDevicesDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual object DomainModule {
    actual val module: Module = module {
        single<DevicesDatabase> { getDevicesDatabase(get()) }
        single<CiderSocket> { param -> CiderSocket(param.get()) }
        factory<CiderApi> { param -> CiderApi(baseUrl = param.get(), token = param.get()) }
    }
}