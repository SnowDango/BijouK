package com.snowdango.bijouk.features.setting

import com.snowdango.bijouk.features.setting.data.VersionData
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


fun settingModule(versionName: String, isDebug: Boolean) = module {
    factory { VersionData(versionName = versionName, isDebug = isDebug) }
    viewModel { SettingViewModel(get()) }
}
