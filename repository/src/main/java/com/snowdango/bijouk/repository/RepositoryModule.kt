package com.snowdango.bijouk.repository

import com.snowdango.bijouk.domain.api.CiderSocket
import com.snowdango.bijouk.domain2.api.CiderApi
import com.snowdango.bijouk.domain2.api.CiderSocket2
import com.snowdango.bijouk.repository.cider.CiderRepository
import com.snowdango.bijouk.repository.device.DevicesRepository
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

object RepositoryModule {
    val module = module {
        factory { DevicesRepository(get()) }
        factory { param ->
            val baseUrl = param.get<String>()
            CiderRepository(
                get<CiderApi> { parametersOf(baseUrl, param.get()) },
                get<CiderSocket> { parametersOf(baseUrl) },
                get<CiderSocket2> { parametersOf(baseUrl) },
            )
        }
    }
}
