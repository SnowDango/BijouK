package com.snowdango.bijouk.features.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.snowdango.bijouk.ui.component.TitleTopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingScreen(
    onClickLicense: () -> Unit,
    viewModel: SettingViewModel = koinViewModel<SettingViewModel>(),
) {
    Scaffold(
        topBar = {
            TitleTopBar(title = "Settings")
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            SettingsMenuLink(
                icon = { Icon(imageVector = Icons.Default.Info, contentDescription = null) },
                title = { Text(text = "オープンソースライセンス") },
                subtitle = { Text(text = "オープンソースライセンスを表示します") },
                onClick = {
                    onClickLicense.invoke()
                },
            )
            HorizontalDivider()
            SettingsMenuLink(
                title = { Text(text = viewModel.versionName) },
                enabled = false,
                onClick = {},
            )
        }
    }
}
