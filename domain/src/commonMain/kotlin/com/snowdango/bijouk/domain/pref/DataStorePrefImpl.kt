package com.snowdango.bijouk.domain.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath


abstract class DataStorePrefImpl {

    abstract fun getDataStorePath(dataStoreName: String): String

    fun getDataStorePref(dataStoreName: String): DataStore<Preferences> =
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { getDataStorePath(dataStoreName).toPath() }
        )

}
