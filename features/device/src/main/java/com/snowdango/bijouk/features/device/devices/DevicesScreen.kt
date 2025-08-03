package com.snowdango.bijouk.features.device.devices

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.features.device.R
import com.snowdango.bijouk.features.device.component.AddDeviceDialog
import com.snowdango.bijouk.features.device.component.DeviceCard
import com.snowdango.bijouk.model.devices.DeviceData
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.component.TitleTopBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,
    onClickDevice: (device: DeviceData) -> Unit,
    onNavigateScan: () -> Unit,
    viewModel: DevicesViewModel = koinViewModel<DevicesViewModel>(),
) {
    val deviceViewData = viewModel.devicesFlow.collectAsStateWithLifecycle()
    val testActive = viewModel.testActiveFlow.collectAsStateWithLifecycle()
    val toastString = viewModel.toastStringFlow.collectAsStateWithLifecycle(initialValue = "")
    var isFloatingExpanded by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(toastString) {
        if (!toastString.value.isBlank()) {
            Toast.makeText(context, toastString.value, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TitleTopBar(
                title = stringResource(R.string.top_bar_title),
            )
        },
        floatingActionButton = {
            FloatingActionButtonMenu(
                expanded = isFloatingExpanded,
                button = {
                    ToggleFloatingActionButton(
                        checked = isFloatingExpanded,
                        onCheckedChange = { isFloatingExpanded = it },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
                    }
                },
                horizontalAlignment = Alignment.End,
            ) {
                FloatingActionButtonMenuItem(
                    text = { Text(text = stringResource(R.string.device_add_menu_qr)) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.QrCode,
                            contentDescription = null,
                        )
                    },
                    onClick = {
                        isFloatingExpanded = false
                        onNavigateScan.invoke()
                    }
                )
                FloatingActionButtonMenuItem(
                    text = { Text(text = stringResource(R.string.device_add_menu_create)) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = null,
                        )
                    },
                    onClick = {
                        isFloatingExpanded = false
                        isDialogOpen = true
                    }
                )
            }
        },
    ) { paddingValues: PaddingValues ->
        PullToRefreshBox(
            modifier = Modifier.padding(paddingValues),
            isRefreshing = deviceViewData.value.isRefreshing,
            onRefresh = { viewModel.refresh() },
        ) {
            Content(
                devices = deviceViewData.value.devices.toImmutableList(),
                onClickDevice = onClickDevice,
            )
        }

        if (isDialogOpen) {
            AddDeviceDialog(
                isTestActive = testActive.value,
                onDismissRequest = {
                    isDialogOpen = false
                    viewModel.clearTestActive()
                },
                onChange = {
                    viewModel.clearTestActive()
                },
                onClickTest = { host, port, token, isUseSsl ->
                    viewModel.testActive(
                        host = host,
                        port = port,
                        token = token,
                        isUseSsl = isUseSsl
                    )
                },
                onClickAdd = { name, host, port, token, isUseSsl ->
                    viewModel.saveDevice(
                        name = name,
                        host = host,
                        port = port,
                        token = token,
                        isUseSsl = isUseSsl,
                    )
                }
            )
        }
    }
}

@Composable
fun Content(
    devices: ImmutableList<DevicesViewModel.ActiveDeviceViewData>,
    modifier: Modifier = Modifier,
    onClickDevice: (DeviceData) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 32.dp, bottom = 76.dp)
        ) {
            items(devices) {
                DeviceCard(it.device, it.isActive) {
                    onClickDevice.invoke(it.device)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewContent() {
    BijouKTheme {
        Content(
            devices = persistentListOf(
                DevicesViewModel.ActiveDeviceViewData(
                    device = DeviceData(
                        id = 1L,
                        name = "古城Bijou",
                        baseUrl = "http://localhost:10767",
                        token = "jngiohaouigbaobgfwaouighwa",
                    ),
                    isActive = false,
                )
            ),
            onClickDevice = {},
        )
    }
}
