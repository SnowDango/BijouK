package com.snowdango.bijouk.domain.pref

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DebugSettingsPref(dataStorePrefImpl: DataStorePrefImpl) : DataStorePref(dataStorePrefImpl) {

    override val dataStoreName: String
        get() = "debug_settings.preferences_pb"

    val isWriteSocketEventLog = booleanDataStoreObject("is_write_socket_event_log")


    fun getData(): Flow<Data> {
        return dataStore.data.map {
            Data(
                isWriteSocketEventLog = it[isWriteSocketEventLog.key] ?: false,
            )
        }
    }

    class Data(
        val isWriteSocketEventLog: Boolean,
    )

}