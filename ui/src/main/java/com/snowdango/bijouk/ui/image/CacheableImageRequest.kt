package com.snowdango.bijouk.ui.image

import android.content.Context
import android.util.Log
import coil3.request.CachePolicy
import coil3.request.ImageRequest

fun cacheableImageRequest(
    context: Context,
    data: Any?,
): ImageRequest.Builder {
    Log.d("cacheableImageRequest", "data: $data")
    return ImageRequest.Builder(context)
        .data(data)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
}
