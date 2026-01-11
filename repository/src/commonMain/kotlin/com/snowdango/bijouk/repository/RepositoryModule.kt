package com.snowdango.bijouk.repository

import com.snowdango.bijouk.api.CiderMusicApi
import com.snowdango.bijouk.api.CiderRpcApi
import com.snowdango.bijouk.domain.api.socket.CiderSocket
import com.snowdango.bijouk.repository.cider.CiderRPCRepository
import com.snowdango.bijouk.repository.cider.CiderRepository
import com.snowdango.bijouk.repository.cider.CiderSocketRepository
import com.snowdango.bijouk.repository.device.DevicesRepository
import com.snowdango.bijouk.repository.settings.DebugSettingsRepository
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

object RepositoryModule {
    val module = module {
        factory { DevicesRepository(get()) }
        factory { param ->
            val baseUrl = param.get<String>()
            CiderRepository(
                get<CiderMusicApi> { parametersOf(baseUrl, param.get()) }
            )
        }
        factory { param ->
            val baseUrl = param.get<String>()
            val token = param.get<String>()
            CiderRPCRepository(
                get<CiderRpcApi> { parametersOf(baseUrl, token) }
            )
        }
        factory { param ->
            CiderSocketRepository(
                get<CiderSocket> { parametersOf(param.get()) },
            )
        }

        factory { DebugSettingsRepository(get()) }
    }
}
