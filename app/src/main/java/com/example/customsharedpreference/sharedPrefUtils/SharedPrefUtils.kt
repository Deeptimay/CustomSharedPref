package com.example.customsharedpreference.sharedPrefUtils

import com.example.customsharedpreference.roomDbSetup.DataTypes
import com.example.customsharedpreference.roomDbSetup.ObjectStore
import com.example.customsharedpreference.roomDbSetup.ObjectStoreDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SharedPrefUtils {
    companion object {
        fun saveDataString(objectStoreDao: ObjectStoreDao, key: String, value: String) {
            genericSaveData(objectStoreDao, key, value, DataTypes.STRING)
        }

        fun saveDataLong(objectStoreDao: ObjectStoreDao, key: String, value: Long) {
            genericSaveData(objectStoreDao, key, value.toString(), DataTypes.LONG)
        }

        fun saveDataInt(objectStoreDao: ObjectStoreDao, key: String, value: Int) {
            genericSaveData(objectStoreDao, key, value.toString(), DataTypes.INT)
        }

        fun saveDataFloat(objectStoreDao: ObjectStoreDao, key: String, value: Double) {
            genericSaveData(objectStoreDao, key, value.toString(), DataTypes.DOUBLE)
        }

        suspend fun getDataInt(
            context: CoroutineContext,
            objectStoreDao: ObjectStoreDao,
            key: String,
            defaultValue: Int
        ): Int {
            var result: Int? = null
            val obj = objectStoreDao.getKeyValue(key)
            try {
                if (obj != null) {
                    if (obj.value != null && obj.type == DataTypes.INT.toString())
                        result = obj.value.toInt()
                }
            } catch (_: Exception) {
            }
            return result ?: defaultValue
        }

        suspend fun getDataSting(
            context: CoroutineContext,
            objectStoreDao: ObjectStoreDao,
            key: String,
            defaultValue: String
        ): String {
            var result: String? = null
            val obj = objectStoreDao.getKeyValue(key)
            try {
                if (obj != null) {
                    if (obj.value != null && obj.type == DataTypes.STRING.toString())
                        result = obj.value.toString()
                }
            } catch (_: Exception) {
            }
            return result ?: defaultValue
        }

        fun getDataLong(objectStoreDao: ObjectStoreDao, key: String, defaultValue: Long): Long {
            var result: Long? = null
            val obj = objectStoreDao.getKeyValue(key)
            try {
                if (obj != null) {
                    if (obj.value != null && obj.type == DataTypes.LONG.toString())
                        result = obj.value.toLong()
                }
            } catch (_: Exception) {
            }
            return result ?: defaultValue
        }

        fun getDataFloat(
            objectStoreDao: ObjectStoreDao,
            key: String,
            defaultValue: Double
        ): Double {
            var result: Double? = null
            val obj = objectStoreDao.getKeyValue(key)
            try {
                if (obj != null) {
                    if (obj.value != null && obj.type == DataTypes.DOUBLE.toString())
                        result = obj.value.toDouble()
                }
            } catch (_: Exception) {
            }
            return result ?: defaultValue
        }

        private fun genericSaveData(
            objectStoreDao: ObjectStoreDao,
            key: String,
            value: String?,
            dataTypes: DataTypes
        ) {
            MainScope().launch(Dispatchers.IO) {
                val objectStore = ObjectStore(key, value, dataTypes.toString())
                val obj = objectStoreDao.getKeyValue(key)
                if (obj == null) {
                    objectStoreDao.insertKeyValue(objectStore)
                } else {
                    objectStoreDao.updateKeyValue(objectStore)
                }
            }
        }
    }
}