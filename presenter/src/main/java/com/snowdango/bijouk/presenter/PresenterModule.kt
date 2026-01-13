package com.snowdango.bijouk.presenter

import com.snowdango.bijouk.features.album.albumModule
import com.snowdango.bijouk.features.artist.artistModule
import com.snowdango.bijouk.features.device.deviceModule
import com.snowdango.bijouk.features.playlist.playlistModule
import com.snowdango.bijouk.features.queue.queueModule
import com.snowdango.bijouk.features.search.searchModule
import com.snowdango.bijouk.features.setting.settingModule
import com.snowdango.bijouk.presenter.second.SecondViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val presenterModule = module {
    viewModel { param -> SecondViewModel(param.get(), param.get()) }
}

fun featureModules(versionName: String, isDebug: Boolean) =
    listOf(
        deviceModule,
        artistModule,
        queueModule,
        searchModule,
        playlistModule,
        albumModule,
        settingModule(versionName, isDebug)
    )

