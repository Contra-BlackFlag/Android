package com.example.liquidglassprojejct.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {
    @Insert
   abstract suspend fun upsertNote(Note : Notes)
    @Delete
    abstract suspend fun updateNote(Note: Notes)
    @Delete
    abstract suspend fun deleteNote(Note: Notes)

    @Query("SELECT * FROM `NotesTable` ORDER BY id ASC")
    abstract fun getNotes(Note: Notes) : Flow<List<Notes>>

    @Query("SELECT * FROM `NotesTable` where id = :id")
    abstract fun getaNotesbyid(id : Long) : Flow<List<Notes>>

}