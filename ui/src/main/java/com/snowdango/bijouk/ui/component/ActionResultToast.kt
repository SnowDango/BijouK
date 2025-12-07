package com.snowdango.bijouk.ui.component

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.snowdango.bijouk.ui.R
import com.snowdango.bijouk.ui.data.ActionResultType

@Composable
fun ActionResultToast(
    actionResultType: ActionResultType,
    onClearActionResult: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(actionResultType) {
        val messageId = when (actionResultType) {
            ActionResultType.SongPlay -> R.string.action_song_toast_play
            ActionResultType.SongPlayNext -> R.string.action_song_toast_play_next
            ActionResultType.SongPlayLater -> R.string.action_song_toast_play_later
            ActionResultType.AlbumPlay -> R.string.action_album_toast_play
            ActionResultType.AlbumPlayShuffled -> R.string.action_album_toast_play_shuffled
            ActionResultType.PlaylistPlay -> R.string.action_playlist_toast_play
            ActionResultType.PlaylistPlayShuffled -> R.string.action_playlist_toast_play
            ActionResultType.None -> null
        }

        messageId?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()
            onClearActionResult.invoke()
        }
    }

}