package com.snowdango.bijouk.domain

import com.snowdango.bijouk.api.CiderMusicApi
import com.snowdango.bijouk.api.CiderRpcApi
import com.snowdango.bijouk.domain.api.getCiderHttpClient
import com.snowdango.bijouk.domain.api.socket.CiderSocket
import com.snowdango.bijouk.domain.db.DevicesDatabase
import com.snowdango.bijouk.domain.db.getDevicesDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual object DomainModule {
    actual val module: Module = module {
        single<DevicesDatabase> { getDevicesDatabase(get()) }
        single<CiderSocket> { param -> CiderSocket(param.get()) }
        factory<CiderRpcApi> { param ->
            val baseUrl = param.get<String>()
            val token = param.get<String>()
            CiderRpcApi(
                baseUrl = "$baseUrl/api/v1",
                httpClient = getCiderHttpClient(baseUrl = baseUrl, token = token)
            )
        }
        factory<CiderMusicApi> {
            val baseUrl = it.get<String>()
            val token = it.get<String>()
            CiderMusicApi(
                baseUrl = "$baseUrl/api/v1",
                httpClient = getCiderHttpClient(baseUrl = baseUrl, token = token)
            )
        }
    }
}