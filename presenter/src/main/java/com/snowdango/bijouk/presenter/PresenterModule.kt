package com.snowdango.bijouk.presenter

import com.snowdango.bijouk.features.album.AlbumModule
import com.snowdango.bijouk.features.artist.ArtistModule
import com.snowdango.bijouk.features.device.DevicesModule
import com.snowdango.bijouk.features.playlist.PlaylistModule
import com.snowdango.bijouk.features.queue.QueueModule
import com.snowdango.bijouk.features.search.SearchModule
import com.snowdango.bijouk.features.setting.SettingModule
import com.snowdango.bijouk.presenter.second.SecondViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object PresenterModule {

    val modules = module {
        viewModel { param -> SecondViewModel(param.get(), param.get()) }
    }

    val deviceModule = DevicesModule.module
    val artistModule = ArtistModule.module
    val queueModule = QueueModule.module
    val searchModule = SearchModule.module
    val playlistModule = PlaylistModule.module
    val albumModule = AlbumModule.module
    fun settingModule(versionName: String, isDebug: Boolean) =
        SettingModule.module(versionName, isDebug)
}
