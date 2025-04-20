package com.snowdango.bijouk.features.setting.view

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.features.setting.R
import com.snowdango.bijouk.ui.component.TitleTopBar
import dev.jeziellago.compose.markdowntext.MarkdownText
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun AppInfoScreen() {
    val imageUri = assetImageCacheUri(LocalContext.current, "app_icon.png")
    Scaffold(
        topBar = {
            TitleTopBar(
                title = stringResource(R.string.app_info_top_bar_title),
            )
        }
    ) { paddingValue ->
        MarkdownText(
            markdown = LocalContext.current.assets.open("app_info.md").bufferedReader()
                .readText().format(imageUri.path),
            style = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .padding(paddingValue)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        )
    }
}

fun assetImageCacheUri(
    context: Context,
    fileName: String,
): Uri {
    val input: InputStream = context.assets.open(fileName)
    val file = File(context.cacheDir, fileName)
    input.use { inputStream ->
        FileOutputStream(file).use { output ->
            val buffer = ByteArray(size = 4 * 1024) // or other buffer size
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }
    }
    return Uri.fromFile(file)
}
