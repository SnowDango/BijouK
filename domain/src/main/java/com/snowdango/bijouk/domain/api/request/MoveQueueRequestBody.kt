package com.snowdango.bijouk.domain.api.request

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MoveQueueRequestBody(
    val startIndex: Int,
    val destinationIndex: Int,
)
