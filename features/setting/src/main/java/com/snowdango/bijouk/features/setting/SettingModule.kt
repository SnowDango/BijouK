package com.snowdango.bijouk.features.setting

import com.snowdango.bijouk.features.setting.data.VersionData
import org.koin.core.module.dsl.viewModel

object SettingModule {

    fun module(versionName: String, isDebug: Boolean) = org.koin.dsl.module {
        factory { VersionData(versionName = versionName, isDebug = isDebug) }
        viewModel { SettingViewModel(get()) }
    }
}
