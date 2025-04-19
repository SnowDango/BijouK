package com.snowdango.bijouk.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.snowdango.bijouk.ui.component.TitleTopBar

@Composable
fun SettingScreen(
    onClickLicense: () -> Unit,
) {
    Scaffold(
        topBar = {
            TitleTopBar(title = "Settings")
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
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
        }
    }
}
