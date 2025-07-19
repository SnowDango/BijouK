package com.snowdango.bijouk.model2.cider.mapper

import com.snowdango.bijouk.domain.api.event.NowPlayingStatusDidChange
import com.snowdango.bijouk.model2.cider.data.NowPlayingStatusData

fun NowPlayingStatusDidChange.convert(): NowPlayingStatusData {
    return NowPlayingStatusData(
        isFav = data.inFavorites,
        isInLib = data.inLibrary,
    )
}
