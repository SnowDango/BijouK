package com.snowdango.bijouk.features.nowPlay

import androidx.annotation.StringRes

enum class ContentPageRoute(@StringRes val titleRes: Int) {
    QUEUE(R.string.content_page_tab_queue),
    SONG(R.string.content_page_tab_song),
    ALBUM(R.string.content_page_tab_album),
    ARTIST(R.string.content_page_tab_artist),
}
