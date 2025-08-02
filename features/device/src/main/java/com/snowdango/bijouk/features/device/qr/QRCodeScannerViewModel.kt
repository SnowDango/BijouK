package com.snowdango.bijouk.features.device.qr

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.features.device.R
import com.snowdango.bijouk.features.device.qr.entity.QRCodeInfo
import com.snowdango.bijouk.infla.SharedEventStore
import com.snowdango.bijouk.model.cider.CiderRPCModel
import com.snowdango.bijouk.model.devices.DevicesModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class QRCodeScannerViewModel : ViewModel(), KoinComponent {

    private val context: Context by inject()
    private val devicesModel: DevicesModel by inject()
    private val sharedEventStore: SharedEventStore by inject()

    private val _testActiveFlow: MutableStateFlow<TestState> =
        MutableStateFlow(value = TestState.None)
    val testActiveFlow = _testActiveFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _testActiveFlow.value,
    )

    private val _saveStateFlow: MutableStateFlow<SaveState> =
        MutableStateFlow(value = SaveState.None)
    val saveStateFlow = _saveStateFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _saveStateFlow.value,
    )

    fun checkQrParse(code: String): Boolean {
        return try {
            val info = Json.decodeFromString<QRCodeInfo>(code)
            testActive(info.address, token = info.token)
            true
        } catch (th: Throwable) {
            Log.e("QRCodeScannerViewModel", th.toString())
            false
        }
    }

    private fun testActive(address: String, port: Int = 10767, token: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val baseUrl = context.getString(
                R.string.url_not_ssl,
                "$address:$port"
            )
            val ciderRPCModel = get<CiderRPCModel> { parametersOf(baseUrl, token) }
            try {
                val action = ciderRPCModel.isActive()
                if (action) {
                    _testActiveFlow.emit(TestState.Success(address, token))
                } else {
                    _testActiveFlow.emit(TestState.Error)
                }
            } catch (ce: CancellationException) {
                throw ce
            } catch (th: Throwable) {
                Log.e("DevicesVieModel", th.toString())
                _testActiveFlow.emit(TestState.Error)
            }
        }

    fun saveDevice(
        name: String,
        host: String,
        token: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            devicesModel.saveDevice(
                name = name,
                host = host,
                port = 10767,
                ssl = false,
                token = token,
            )
            _saveStateFlow.emit(SaveState.Success)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.DeviceListUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("DevicesViewModel", th.toString())
            _saveStateFlow.emit(SaveState.Error)
        }
    }

    sealed class TestState {
        data object None : TestState()
        data class Success(val host: String, val token: String) : TestState()
        data object Error : TestState()
    }

    sealed class SaveState {
        data object None : SaveState()
        data object Success : SaveState()
        data object Error : SaveState()
    }
}
