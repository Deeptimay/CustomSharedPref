package com.example.customsharedpreference.roomDbSetup

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ObjectStoreDao {
    @Query("SELECT * FROM data_storage_table where `Key` = :key")
    fun getKeyValue(key: String): ObjectStore?

    @Update
    fun updateKeyValue(objectStore: ObjectStore)

    @Insert
    fun insertKeyValue(objectStore: ObjectStore)

    @Delete
    fun delete(objectStore: ObjectStore)
}