package com.example.browser.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface userHistoryDao {
    @Insert
    suspend fun insertHistory(userHistory: userHistory)

    @Update
    suspend fun updateHistory(userHistory: userHistory)

    @Delete
    suspend fun deleteHistory(userHistory: userHistory)

    @Query("SELECT * FROM useshistory")
    fun getHistory() : LiveData<List<userHistory>>
}