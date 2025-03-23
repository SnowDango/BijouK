package com.snowdango.bijouk.features.device

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.features.device.component.AddDeviceDialog
import com.snowdango.bijouk.features.device.component.DeviceCard
import com.snowdango.bijouk.model.devices.DeviceData
import com.snowdango.bijouk.ui.BijouKTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,
    viewModel: DevicesViewModel = koinViewModel<DevicesViewModel>(),
    onClickDevice: (device: DeviceData) -> Unit,
) {
    val deviceViewData = viewModel.devicesFlow.collectAsStateWithLifecycle()
    val testActive = viewModel.testActiveFlow.collectAsStateWithLifecycle()
    val toastString = viewModel.toastStringFlow.collectAsStateWithLifecycle(initialValue = "")
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
            TopBar()
        }
    ) { paddingValues: PaddingValues ->
        PullToRefreshBox(
            modifier = Modifier.padding(paddingValues),
            isRefreshing = deviceViewData.value.isRefreshing,
            onRefresh = { viewModel.refresh() },
        ) {
            Content(
                devices = deviceViewData.value.devices.toImmutableList(),
                onClickAdd = {
                    isDialogOpen = true
                },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Devices",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
    )
}

@Composable
fun Content(
    devices: ImmutableList<DevicesViewModel.ActiveDeviceViewData>,
    onClickAdd: () -> Unit,
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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 32.dp, end = 32.dp)
                    .size(60.dp),
                shape = CircleShape,
                onClick = onClickAdd,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview_TopBar() {
    BijouKTheme {
        TopBar()
    }
}


@Preview
@Composable
private fun Preview_Content() {
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
            onClickAdd = {},
            onClickDevice = {},
        )
    }
}