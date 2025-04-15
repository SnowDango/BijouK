package com.snowdango.bijouk.features.nowPlay

import androidx.annotation.StringRes

enum class ContentPageRoute(@StringRes val titleRes: Int) {
    QUEUE(R.string.content_page_tab_queue),
    SONG(R.string.content_page_tab_song),
    PLAYLIST(R.string.content_page_tab_playlist),
    ALBUM(R.string.content_page_tab_album),
    ARTIST(R.string.content_page_tab_artist),
}
