package com.example.browser.Database

class UserHistoryRepository(private val dao: userHistoryDao) {

    val getAllHistory = dao.getHistory()

    suspend fun insertHistory(history: userHistory) {
        dao.insertHistory(history)
    }

    suspend fun updateHistory(history: userHistory) {
        dao.updateHistory(history)
    }

    suspend fun deleteHistory(history: userHistory) {
        dao.deleteHistory(history)
    }
}
