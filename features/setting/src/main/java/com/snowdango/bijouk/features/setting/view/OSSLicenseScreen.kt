package com.snowdango.bijouk.features.setting.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.snowdango.bijouk.ui.component.TitleTopBar

@Composable
fun OSSLicenseScreen(
    libs: Libs,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TitleTopBar(title = "Licenses")
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
