package com.snowdango.bijouk.domain2

import com.snowdango.bijouk.domain2.api.CiderApi
import com.snowdango.bijouk.domain2.api.CiderSocket2
import com.snowdango.bijouk.domain2.db.DevicesDatabase
import com.snowdango.bijouk.domain2.db.getDevicesDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual object DomainModule2 {
    actual val module: Module = module {
        single<DevicesDatabase> { getDevicesDatabase(get()) }
        single<CiderSocket2> { param -> CiderSocket2(param.get()) }
        factory<CiderApi> { param -> CiderApi(baseUrl = param.get(), token = param.get()) }
    }
}