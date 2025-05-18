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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.snowdango.bijouk.ui.component.TitleTopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingScreen(
    onClickLicense: () -> Unit,
    modifier: Modifier = Modifier,
    onClickAppInfo: () -> Unit,
    viewModel: SettingViewModel = koinViewModel<SettingViewModel>(),
) {
    Scaffold(
        topBar = {
            TitleTopBar(title = stringResource(R.string.setting_top_bar_title))
        },
        modifier = modifier,
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
        ) {
            SettingsMenuLink(
                icon = { Icon(imageVector = Icons.Default.Info, contentDescription = null) },
                title = { Text(text = stringResource(R.string.setting_column_app_info)) },
                subtitle = { Text(text = stringResource(R.string.setting_column_app_info_description)) },
                onClick = {
                    onClickAppInfo.invoke()
                }
            )
            SettingsMenuLink(
                icon = { Icon(imageVector = Icons.Default.Info, contentDescription = null) },
                title = { Text(text = stringResource(R.string.setting_column_oss_license)) },
                subtitle = { Text(text = stringResource(R.string.setting_column_oss_license_description)) },
                onClick = {
                    onClickLicense.invoke()
                },
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            SettingsMenuLink(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                title = { Text(text = viewModel.versionName) },
                enabled = false,
                onClick = {},
            )
        }
    }
}
