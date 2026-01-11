package com.snowdango.bijouk.repository.settings

import com.snowdango.bijouk.domain.pref.DebugSettingsPref
import kotlinx.coroutines.flow.Flow

class DebugSettingsRepository(
    val debugSettingsPref: DebugSettingsPref,
) {

    fun getIsWriteSocketEventLog(): Flow<Boolean> =
        debugSettingsPref.isWriteSocketEventLog.get()

    suspend fun setIsWriteSocketEventLog(value: Boolean) {
        debugSettingsPref.isWriteSocketEventLog.set(value)
    }

    fun getFlowData(): Flow<DebugSettingsPref.Data> =
        debugSettingsPref.getData()

}