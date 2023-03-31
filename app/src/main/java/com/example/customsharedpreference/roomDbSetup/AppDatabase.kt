package com.example.customsharedpreference.roomDbSetup

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ObjectStore::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun objectStoreDao(): ObjectStoreDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
        }
    }
}