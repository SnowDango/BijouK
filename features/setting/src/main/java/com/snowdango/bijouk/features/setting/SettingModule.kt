package com.snowdango.bijouk.features.setting


import com.snowdango.bijouk.features.setting.data.VersionData
import org.koin.core.module.dsl.viewModel

object SettingModule {

    fun module(versionName: String) = org.koin.dsl.module {
        factory { VersionData(versionName = versionName) }
        viewModel { SettingViewModel(get()) }
    }

}