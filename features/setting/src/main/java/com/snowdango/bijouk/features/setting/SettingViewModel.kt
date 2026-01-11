package com.snowdango.bijouk.features.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.features.setting.data.VersionData
import com.snowdango.bijouk.model.settings.DebugSettingsData
import com.snowdango.bijouk.model.settings.DebugSettingsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingViewModel(
    versionData: VersionData,
) : ViewModel(), KoinComponent {

    private val debugSettingsModel: DebugSettingsModel by inject()

    val versionName = versionData.versionName

    val debugSettingsFlow: Flow<DebugSettingsData?> = debugSettingsModel.getFlowData()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null,
        )

    fun setIsWriteSocketEventLog(value: Boolean) = viewModelScope.launch {
        debugSettingsModel.setIsWriteSocketEventLog(value)
    }
}
