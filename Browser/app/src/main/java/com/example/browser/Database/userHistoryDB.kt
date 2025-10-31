package com.example.browser.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [userHistory::class], version = 1, exportSchema = false)
abstract class userHistoryDB : RoomDatabase() {

    abstract fun userHistoryDao(): userHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: userHistoryDB? = null

        fun getDatabase(context: Context): userHistoryDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    userHistoryDB::class.java,
                    "user_history_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
