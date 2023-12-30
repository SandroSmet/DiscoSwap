package com.example.discoswap.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: DbMessage)

    @Query("SELECT * FROM messages")
    fun getMessages(): Flow<List<DbMessage>>

    @Query("SELECT * FROM messages WHERE id = :id")
    fun getMessageDetails(id: String): Flow<DbMessage>
}