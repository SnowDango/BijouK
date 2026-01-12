package com.snowdango.bijouk.model.cider

import com.snowdango.bijouk.model.devices.DeviceData
import com.snowdango.bijouk.repository.cider.CiderRPCRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class CiderMultiModel(
    devices: List<DeviceData>,
) : KoinComponent {

    private val repositories: Map<DeviceData, CiderRPCRepository> = devices.associateWith {
        get<CiderRPCRepository> { parametersOf(it.baseUrl, it.token) }
    }

    suspend fun isActives(): List<Long> {
        return repositories.map {
            val isActive = try {
                it.value.getActive()
                true
            } catch (_: Throwable) {
                false
            }
            it.key.id to isActive
        }.filter { it.second }.map { it.first }
    }
}
