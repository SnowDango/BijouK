package com.snowdango.bijouk.model2

import com.snowdango.bijouk.model2.cider.CiderModel
import com.snowdango.bijouk.model2.cider.CiderMultiModel
import com.snowdango.bijouk.model2.devices.DevicesModel
import org.koin.dsl.module

object ModelModule {

    val module = module {
        factory { DevicesModel() }
        factory { param -> CiderMultiModel(param.get()) }
        factory { param -> CiderModel(param.get(), param.get()) }
    }

}