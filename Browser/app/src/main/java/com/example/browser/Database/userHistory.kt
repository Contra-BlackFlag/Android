package com.example.browser.Database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "useshistory")
data class userHistory(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val title : String,
    val url : String
)
