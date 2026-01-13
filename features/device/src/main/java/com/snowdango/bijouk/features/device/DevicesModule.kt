package com.snowdango.bijouk.features.device

import com.snowdango.bijouk.features.device.devices.DevicesViewModel
import com.snowdango.bijouk.features.device.qr.QRCodeScannerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val deviceModule = module {
    viewModelOf(::DevicesViewModel)
    viewModelOf(::QRCodeScannerViewModel)
}
