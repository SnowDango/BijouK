package com.snowdango.bijouk.model

import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.CiderMultiModel
import com.snowdango.bijouk.model.devices.DevicesModel
import org.koin.dsl.module

object ModelModule {

    val module = module {
        factory { DevicesModel() }
        factory { param -> CiderMultiModel(param.get()) }
        factory { param -> CiderModel(param.get(), param.get()) }
    }

}