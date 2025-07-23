package com.snowdango.bijouk.presenter.second

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.presenter.second.content.SecondScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

class SecondActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val secondActivityData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable(KEY_DEVICE_DATA, SecondActivityData::class.java)
        } else {
            intent.extras?.getParcelable(KEY_DEVICE_DATA)
        } ?: throw IllegalArgumentException("Device data is required")
        setContent {
            val viewModel = koinViewModel<SecondViewModel> {
                parametersOf(secondActivityData.baseUrl, secondActivityData.token)
            }
            val connectionState = viewModel.connectionStateFlow.collectAsStateWithLifecycle()
            val nowPlayData = viewModel.nowPlayFlow.collectAsStateWithLifecycle()
            val playbackTimeData = viewModel.playBackTimeData.collectAsStateWithLifecycle()
            val nowPlayingStatusData = viewModel.nowPlayingStatusFlow.collectAsStateWithLifecycle()
            SecondScreen(
                connectionState.value,
                nowPlayData.value,
                playbackTimeData.value,
                nowPlayingStatusData.value,
                modifier = Modifier.fillMaxSize(),
            ) { sheetMinHeight ->
                
            }
        }
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