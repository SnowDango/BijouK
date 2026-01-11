package com.snowdango.bijouk.domain.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class DataStorePref(dataStorePrefImpl: DataStorePrefImpl) {

    abstract val dataStoreName: String

    val dataStore = dataStorePrefImpl.getDataStorePref(dataStoreName)

    fun booleanDataStoreObject(memberName: String) = BooleanDataStoreObject(memberName, dataStore)
    class BooleanDataStoreObject(
        memberName: String,
        val dataStore: DataStore<Preferences>
    ) {
        val key: Preferences.Key<Boolean> = booleanPreferencesKey(memberName)

        fun get(): Flow<Boolean> {
            return dataStore.data.map {
                it[key] ?: false
            }
        }

        suspend fun set(value: Boolean) {
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }
}