package com.example.liquidglassprojejct.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "NotesTable")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "Title")
    val title :String = "",
    @ColumnInfo(name = "Content")
    val content : String = ""
)
