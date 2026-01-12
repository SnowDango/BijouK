package com.snowdango.bijouk.presenter.second

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SecondActivityData(
    val name: String,
    val baseUrl: String,
    val token: String,
) : Parcelable
