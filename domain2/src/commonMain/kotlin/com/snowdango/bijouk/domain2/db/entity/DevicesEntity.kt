package com.snowdango.bijouk.domain2.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DevicesEntity.devices)
data class DevicesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DevicesEntity.id)
    val id: Long = 0L,
    @ColumnInfo(name = DevicesEntity.host)
    val host: String,
    @ColumnInfo(name = DevicesEntity.port)
    val port: Int?,
    @ColumnInfo(name = DevicesEntity.ssl)
    val ssl: Boolean,
    @ColumnInfo(name = DevicesEntity.name)
    val name: String,
    @ColumnInfo(name = DevicesEntity.token)
    val token: String,
) {
    companion object {
        const val devices = "devices"
        const val id = "id"
        const val host = "host"
        const val port = "port"
        const val ssl = "ssl"
        const val name = "name"
        const val token = "token"
    }
}
