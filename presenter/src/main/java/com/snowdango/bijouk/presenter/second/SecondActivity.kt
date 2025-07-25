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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.snowdango.bijouk.features.artist.ArtistsDetailScreen
import com.snowdango.bijouk.features.queue.QueueScreen
import com.snowdango.bijouk.features.search.SearchScreen
import com.snowdango.bijouk.presenter.second.content.SecondScreen
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.extend.ScreenType
import com.snowdango.bijouk.ui.extend.SetOrientation
import com.snowdango.bijouk.ui.extend.screenType
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

        setContent {
            SetOrientation()
            val screenType = screenType()

            val connectionState = viewModel.connectionStateFlow.collectAsStateWithLifecycle()
            val isEnableChange = viewModel.isChangeableSeekFlow.collectAsStateWithLifecycle()
            val nowPlayData = viewModel.nowPlayFlow.collectAsStateWithLifecycle()
            val playbackTimeData = viewModel.playBackTimeData.collectAsStateWithLifecycle()
            val nowPlayingStatusData = viewModel.nowPlayingStatusFlow.collectAsStateWithLifecycle()

            BijouKTheme {
                SecondScreen(
                    screenType = screenType,
                    name = secondActivityData.name,
                    connectionState.value,
                    isEnableChange.value,
                    nowPlayData.value,
                    playbackTimeData.value,
                    nowPlayingStatusData.value,
                    onPlayPause = viewModel::playPause,
                    onSeekTo = viewModel::seekTo,
                    onNext = viewModel::next,
                    onPrevious = viewModel::prev,
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
                            )
                        }
                        composable<SecondRoute.SEARCH> {
                            SearchScreen(
                                baseUrl = secondActivityData.baseUrl,
                                token = secondActivityData.token,
                                sheetMinSize = sheetMinHeight,
                                onNavigateArtist = {
                                    navController.navigate(SecondRoute.ARTIST(artistId = it))
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
