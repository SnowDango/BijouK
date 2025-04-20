package com.snowdango.bijouk.features.setting.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.snowdango.bijouk.features.setting.R
import com.snowdango.bijouk.ui.component.TitleTopBar

@Composable
fun OSSLicenseScreen(
    libs: Libs,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TitleTopBar(title = stringResource(R.string.license_top_bar_title))
        },
    ) { paddingValue ->
        LibrariesContainer(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize(),
            libraries = libs,
        )
    }
}
