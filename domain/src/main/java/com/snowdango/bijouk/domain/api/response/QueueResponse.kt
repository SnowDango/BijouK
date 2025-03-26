package com.snowdango.bijouk.domain.api.response

import android.annotation.SuppressLint
import com.snowdango.bijouk.domain.api.response.data.QueueResponseData
import kotlinx.serialization.Serializable


@SuppressLint("UnsafeOptInUsageError")
@Serializable
class QueueResponse(override val size: Int) : List<QueueResponseData> {
    override fun contains(element: QueueResponseData): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<QueueResponseData>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): QueueResponseData {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: QueueResponseData): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<QueueResponseData> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: QueueResponseData): Int {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<QueueResponseData> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<QueueResponseData> {
        TODO("Not yet implemented")
    }

    override fun subList(
        fromIndex: Int,
        toIndex: Int
    ): List<QueueResponseData> {
        TODO("Not yet implemented")
    }
}