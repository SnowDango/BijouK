package com.snowdango.bijouk.features.device.qr

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.features.device.R
import com.snowdango.bijouk.features.device.qr.composable.EnterNameDialog
import com.snowdango.bijouk.ui.BijouKTheme
import org.koin.compose.viewmodel.koinViewModel
import org.publicvalue.multiplatform.qrcode.CameraPosition
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions

@Composable
fun QRCodeScannerScreen(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    viewModel: QRCodeScannerViewModel = koinViewModel<QRCodeScannerViewModel>()
) {
    val context = LocalContext.current
    val testActionState = viewModel.testActiveFlow.collectAsStateWithLifecycle()
    val saveState = viewModel.saveStateFlow.collectAsStateWithLifecycle()
    var isDialogOpen: QRCodeScannerViewModel.TestState.Success? by remember { mutableStateOf(null) }

    LaunchedEffect(testActionState.value) {
        if (testActionState.value is QRCodeScannerViewModel.TestState.Success) {
            isDialogOpen = testActionState.value as QRCodeScannerViewModel.TestState.Success
        } else if (testActionState.value is QRCodeScannerViewModel.TestState.Error) {
            Toast.makeText(
                context,
                context.getString(R.string.toast_qr_code_device_connect_error),
                Toast.LENGTH_SHORT
            ).show()
            onNavigationBack.invoke()
        }
    }

    LaunchedEffect(saveState.value) {
        if (saveState.value is QRCodeScannerViewModel.SaveState.Success) {
            onNavigationBack.invoke()
        } else if (saveState.value is QRCodeScannerViewModel.SaveState.Error) {
            Toast.makeText(
                context,
                context.getString(R.string.toast_qr_code_save_device_error),
                Toast.LENGTH_SHORT
            ).show()
            onNavigationBack.invoke()
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            ScannerWithPermissions(
                onScanned = {
                    viewModel.checkQrParse(it)
                },
                types = listOf(CodeType.QR),
                cameraPosition = CameraPosition.BACK,
            )
        }

        isDialogOpen?.let { state ->
            EnterNameDialog(
                onDismissRequest = {
                    isDialogOpen = null
                },
                onConfirm = {
                    viewModel.saveDevice(
                        it,
                        state.host,
                        state.token,
                    )
                },
                onCancel = {
                    onNavigationBack.invoke()
                }
            )
        }
    }
}

@Preview
@Composable
fun QRCodeScannerScreenPreview() {
    BijouKTheme {
        QRCodeScannerScreen(
            onNavigationBack = {}
        )
    }
}
