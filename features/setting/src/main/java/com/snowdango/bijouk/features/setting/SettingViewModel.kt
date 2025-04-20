package com.snowdango.bijouk.features.setting

import androidx.lifecycle.ViewModel
import com.snowdango.bijouk.features.setting.data.VersionData

class SettingViewModel(
    versionData: VersionData,
) : ViewModel() {

    val versionName = versionData.versionName

}