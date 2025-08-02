package com.snowdango.bijouk.model

import com.snowdango.bijouk.model.cider.CiderBridgeModel
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.CiderMultiModel
import com.snowdango.bijouk.model.cider.CiderRPCModel
import com.snowdango.bijouk.model.cider.CiderSocketModel
import com.snowdango.bijouk.model.devices.DevicesModel
import org.koin.dsl.module

object ModelModule {

    val module = module {
        factory { DevicesModel() }
        factory { param -> CiderMultiModel(param.get()) }
        factory { param -> CiderModel(param.get(), param.get()) }
        factory { param -> CiderRPCModel(param.get(), param.get()) }
        factory { param -> CiderSocketModel(param.get()) }
        factory { param -> CiderBridgeModel(param.get(), param.get()) }
    }

}