package com.snowdango.bijouk.features.device

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.CiderMultiModel
import com.snowdango.bijouk.model.devices.DeviceData
import com.snowdango.bijouk.model.devices.DevicesModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class DevicesViewModel : ViewModel(), KoinComponent {

    private val context: Context by inject()
    private val devicesModel: DevicesModel by inject()
    private var ciderMultiModel: CiderMultiModel =
        get<CiderMultiModel> { parametersOf(listOf<DeviceData>()) }

    private val _devicesFlow: MutableStateFlow<List<DeviceData>> =
        MutableStateFlow(value = listOf())
    private val _activesFlow: MutableStateFlow<List<Long>> = MutableStateFlow(value = listOf())
    private val _isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val devicesFlow: StateFlow<DeviceViewData> =
        _devicesFlow.combine(_activesFlow) { devices, actives ->
            devices.map { ActiveDeviceViewData(device = it, isActive = actives.contains(it.id)) }
        }.combine(_isRefreshing) { devices, isRefresh ->
            DeviceViewData(devices = devices, isRefreshing = isRefresh)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            DeviceViewData(devices = listOf(), isRefreshing = false),
        )
    private val _testActiveFlow: MutableStateFlow<Boolean?> = MutableStateFlow(value = null)
    val testActiveFlow = _testActiveFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _testActiveFlow.value,
    )
    private val _toastStringFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val toastStringFlow = _toastStringFlow.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
    )

    init {
        load()
    }

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val devices = devicesModel.loadDevice()
                _devicesFlow.emit(devices)
                ciderMultiModel = get<CiderMultiModel> { parametersOf(_devicesFlow.value) }
                getActives()
            } catch (ce: CancellationException) {
                throw ce
            } catch (th: Throwable) {
                Log.e("DevicesViewModel", th.toString())
            }
        }
    }

    fun refresh() {
        _isRefreshing.tryEmit(true)
        viewModelScope.launch(Dispatchers.IO) {
            load()
            _isRefreshing.emit(false)
        }
    }

    fun getActives() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val actives = ciderMultiModel.isActives()
            _activesFlow.emit(actives)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("DevicesVieModel", th.toString())
            _toastStringFlow.emit(context.getString(R.string.toast_get_active_status_failed))
        }
    }

    fun testActive(host: String, port: Int?, token: String, isUseSsl: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            val baseUrl = context.getString(
                if (isUseSsl) {
                    R.string.url_ssl
                } else {
                    R.string.url_not_ssl
                },
                if (port == null) {
                    host
                } else {
                    "$host:$port"
                }
            )
            val ciderModel = get<CiderModel> { parametersOf(baseUrl, token) }
            try {
                val active = ciderModel.isActive()
                _testActiveFlow.emit(active)
            } catch (ce: CancellationException) {
                throw ce
            } catch (th: Throwable) {
                Log.e("DevicesVieModel", th.toString())
                _testActiveFlow.emit(false)
            }
        }

    fun clearTestActive() = viewModelScope.launch(Dispatchers.IO) {
        _testActiveFlow.emit(null)
    }

    fun saveDevice(
        name: String,
        host: String,
        port: Int?,
        token: String,
        isUseSsl: Boolean
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            devicesModel.saveDevice(
                name = name,
                host = host,
                port = port,
                ssl = isUseSsl,
                token = token,
            )
            load()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("DevicesViewModel", th.toString())
            _toastStringFlow.emit(context.getString(R.string.toast_save_device_failed))
        }
    }

    data class DeviceViewData(
        val devices: List<ActiveDeviceViewData>,
        val isRefreshing: Boolean,
    )

    data class ActiveDeviceViewData(
        val device: DeviceData,
        val isActive: Boolean,
    )
}
