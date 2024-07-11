package com.sewain.mobileapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sewain.mobileapp.data.local.entity.CatalogEntity
import com.sewain.mobileapp.data.local.entity.RemoteKeys

@Database(entities = [CatalogEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class SewainDatabase : RoomDatabase() {
    abstract fun catalogDao(): CatalogDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var instance: SewainDatabase? = null
        fun getInstance(context: Context): SewainDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    SewainDatabase::class.java, "sewaindb"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
    }
}