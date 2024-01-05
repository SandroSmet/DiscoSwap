package com.example.discoswap.data.message

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for handling operations related to messages in the database.
 */
@Dao
interface MessageDao {
    /**
     * Insert a message into the database.
     *
     * @param message the message to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: DbMessage)

    /**
     * Get the list of messages as a flow.
     *
     * @return a Flow emitting the list of messages
     */
    @Query("SELECT * FROM messages")
    fun getMessages(): Flow<List<DbMessage>>

    /**
     * Get details of a specific message as a flow.
     *
     * @param id the ID of the message
     * @return a Flow emitting the details of the message with the given ID
     */
    @Query("SELECT * FROM messages WHERE id = :id")
    fun getMessageDetails(id: String): Flow<DbMessage>
}
