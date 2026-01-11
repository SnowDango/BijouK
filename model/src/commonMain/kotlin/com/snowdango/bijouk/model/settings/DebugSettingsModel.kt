package com.snowdango.bijouk.model.settings

import com.snowdango.bijouk.repository.settings.DebugSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DebugSettingsModel : KoinComponent {

    private val debugSettingsRepository: DebugSettingsRepository by inject()

    fun getIsWriteSocketEventLog(): Flow<Boolean> =
        debugSettingsRepository.getIsWriteSocketEventLog()

    suspend fun setIsWriteSocketEventLog(value: Boolean) {
        debugSettingsRepository.setIsWriteSocketEventLog(value)
    }

    fun getFlowData(): Flow<DebugSettingsData> =
        debugSettingsRepository.getFlowData().map {
            DebugSettingsData(
                isWriteSocketEventLog = it.isWriteSocketEventLog,
            )
        }

}