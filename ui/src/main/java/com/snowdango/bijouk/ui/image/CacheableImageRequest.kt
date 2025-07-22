package com.snowdango.bijouk.ui.image

import android.content.Context
import coil3.request.CachePolicy
import coil3.request.ImageRequest

fun cacheableImageRequest(
    context: Context,
    data: Any?,
): ImageRequest.Builder {
    return ImageRequest.Builder(context)
        .data(data)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
}
