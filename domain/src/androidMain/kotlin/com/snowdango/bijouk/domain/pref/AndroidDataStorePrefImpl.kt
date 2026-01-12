package com.snowdango.bijouk.domain.pref

import android.content.Context


class AndroidDataStorePrefImpl(private val context: Context) :
    DataStorePrefImpl() {

    fun getAndroidDataStorePath(context: Context, dataStoreName: String): String {
        return context.filesDir.resolve(dataStoreName).absolutePath
    }

    override fun getDataStorePath(dataStoreName: String): String {
        return getAndroidDataStorePath(context, dataStoreName)
    }

}