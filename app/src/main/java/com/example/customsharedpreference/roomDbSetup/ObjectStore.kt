package com.example.customsharedpreference.roomDbSetup

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_storage_table")
data class ObjectStore(
    @PrimaryKey
    @ColumnInfo(name = "Key")
    val Key: String,
    @ColumnInfo(name = "value")
    val value: String?,
    @ColumnInfo(name = "type")
    val type: String
)
