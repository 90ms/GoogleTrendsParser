package com.a90ms.nowrtf.parser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CachedFeed::class], version = 1)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun cachedProjectsDao(): CachedFeedDao

    companion object {
        private var INSTANCE: CacheDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): CacheDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            CacheDatabase::class.java, "rssparsercache.db")
                            .build()
                    }
                    return INSTANCE as CacheDatabase
                }
            }
            return INSTANCE as CacheDatabase
        }
    }
}