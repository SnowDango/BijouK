package com.snowdango.bijouk.presenter.second

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.snowdango.bijouk.features.album.AlbumDetailScreen
import com.snowdango.bijouk.features.artist.ArtistsDetailScreen
import com.snowdango.bijouk.features.playlist.PlaylistScreen
import com.snowdango.bijouk.features.queue.QueueScreen
import com.snowdango.bijouk.features.search.LibrarySearchScreen
import com.snowdango.bijouk.features.search.SearchScreen
import com.snowdango.bijouk.presenter.notification.RemoteMediaService
import com.snowdango.bijouk.presenter.second.content.SecondScreen
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.extend.ScreenType
import com.snowdango.bijouk.ui.extend.SetOrientation
import com.snowdango.bijouk.ui.extend.screenType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SecondActivity : ComponentActivity() {

    private lateinit var secondActivityData: SecondActivityData
    private val viewModel: SecondViewModel by viewModel {
        parametersOf(secondActivityData.baseUrl, secondActivityData.token)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        secondActivityData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable(KEY_DEVICE_DATA, SecondActivityData::class.java)
        } else {
            intent.extras?.getParcelable(KEY_DEVICE_DATA)
        } ?: throw IllegalArgumentException("Device data is required")
        onStartService()
        setContent {
            SetOrientation()
            val screenType = screenType()

            val connectionState = viewModel.connectionStateFlow.collectAsStateWithLifecycle()
            val isEnableChange = viewModel.isChangeableSeekFlow.collectAsStateWithLifecycle()
            val nowPlayData = viewModel.nowPlayFlow.collectAsStateWithLifecycle()
            val playbackTimeData = viewModel.playBackTimeData.collectAsStateWithLifecycle()
            val nowPlayingStatusData = viewModel.nowPlayingStatusFlow.collectAsStateWithLifecycle()
            val isShuffled = viewModel.isShuffledFlow.collectAsStateWithLifecycle()

            BijouKTheme {
                SecondScreen(
                    screenType = screenType,
                    name = secondActivityData.name,
                    connectionState = connectionState.value,
                    isEnableChange = isEnableChange.value,
                    nowPlayData = nowPlayData.value,
                    playBackTimeData = playbackTimeData.value,
                    nowPlayingStatusData = nowPlayingStatusData.value,
                    isShuffled = isShuffled.value,
                    onPlayPause = viewModel::playPause,
                    onSeekTo = viewModel::seekTo,
                    onNext = viewModel::next,
                    onPrevious = viewModel::prev,
                    onClickShuffle = viewModel::onShuffleToggle,
                    modifier = Modifier.fillMaxSize()
                ) { sheetMinHeight ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = SecondRoute.QUEUE,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        composable<SecondRoute.QUEUE> {
                            QueueScreen(
                                isNeedTitle = screenType == ScreenType.SINGLE,
                                name = secondActivityData.name,
                                baseUrl = secondActivityData.baseUrl,
                                token = secondActivityData.token,
                                sheetMinSize = sheetMinHeight,
                                modifier = Modifier.fillMaxSize(),
                                onNavigateSearch = {
                                    navController.navigate(SecondRoute.SEARCH)
                                },
                                onNavigateLibrary = {
                                    navController.navigate(SecondRoute.LIBRARY)
                                },
                            )
                        }
                        composable<SecondRoute.SEARCH> {
                            SearchScreen(
                                baseUrl = secondActivityData.baseUrl,
                                token = secondActivityData.token,
                                sheetMinSize = sheetMinHeight,
                                onNavigateAlbum = {
                                    navController.navigate(
                                        SecondRoute.ALBUM(albumId = it, isLibrary = false)
                                    )
                                },
                                onNavigateArtist = {
                                    navController.navigate(
                                        SecondRoute.ARTIST(artistId = it, isLibrary = false)
                                    )
                                },
                                onNavigatePlaylist = {
                                    navController.navigate(
                                        SecondRoute.PLAYLIST(
                                            playlistId = it,
                                            isLibrary = false
                                        )
                                    )
                                },
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                        composable<SecondRoute.LIBRARY> {
                            LibrarySearchScreen(
                                baseUrl = secondActivityData.baseUrl,
                                token = secondActivityData.token,
                                sheetMinSize = sheetMinHeight,
                                modifier = Modifier.fillMaxSize(),
                                onNavigationAlbum = { albumId, isLibrary ->
                                    navController.navigate(
                                        SecondRoute.ALBUM(
                                            albumId = albumId,
                                            isLibrary = isLibrary,
                                        )
                                    )
                                },
                                onNavigateArtist = { artistId, isLibrary ->
                                    navController.navigate(
                                        SecondRoute.ARTIST(
                                            artistId = artistId,
                                            isLibrary = isLibrary
                                        )
                                    )
                                },
                                onNavigatePlaylist = { playlistId, isLibrary ->
                                    navController.navigate(
                                        SecondRoute.PLAYLIST(
                                            playlistId = playlistId,
                                            isLibrary = isLibrary,
                                        )
                                    )
                                }
                            )
                        }
                        composable<SecondRoute.ALBUM> { backStackEntry ->
                            val album = backStackEntry.toRoute<SecondRoute.ALBUM>()
                            AlbumDetailScreen(
                                baseUrl = secondActivityData.baseUrl,
                                token = secondActivityData.token,
                                albumId = album.albumId,
                                isLibrary = album.isLibrary,
                                sheetMinSize = sheetMinHeight,
                                onNavigationBack = {
                                    navController.popBackStack()
                                },
                                modifier = Modifier.fillMaxSize(),
                            )
                        }

                        composable<SecondRoute.ARTIST> { backStackEntry ->
                            val artist = backStackEntry.toRoute<SecondRoute.ARTIST>()
                            ArtistsDetailScreen(
                                baseUrl = secondActivityData.baseUrl,
                                token = secondActivityData.token,
                                artistId = artist.artistId,
                                isLibrary = artist.isLibrary,
                                sheetMinSize = sheetMinHeight,
                                onNavigationBack = {
                                    navController.popBackStack()
                                },
                                onNavigationAlbum = { albumId, isLibrary ->
                                    navController.navigate(
                                        SecondRoute.ALBUM(
                                            albumId = albumId,
                                            isLibrary = isLibrary,
                                        )
                                    )
                                },
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                        composable<SecondRoute.PLAYLIST> { backStackEntry ->
                            val playlist = backStackEntry.toRoute<SecondRoute.PLAYLIST>()
                            PlaylistScreen(
                                baseUrl = secondActivityData.baseUrl,
                                token = secondActivityData.token,
                                playlistId = playlist.playlistId,
                                isLibrary = playlist.isLibrary,
                                sheetMinSize = sheetMinHeight,
                                onNavigationBack = {
                                    navController.popBackStack()
                                },
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                }
            }
        }
    }

    fun onStartService() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.connectionStateFlow.collect {
                if (it) {
                    RemoteMediaService.startService(
                        applicationContext,
                        secondActivityData.baseUrl,
                        secondActivityData.token
                    )
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    companion object {
        private const val KEY_DEVICE_DATA = "device_data"
        fun start(context: Context, secondActivityData: SecondActivityData) {
            val intent = Intent(context, SecondActivity::class.java).apply {
                putExtra(KEY_DEVICE_DATA, secondActivityData)
            }
            context.startActivity(intent)
        }
    }
}
