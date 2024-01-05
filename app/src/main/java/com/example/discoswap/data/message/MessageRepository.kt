package com.example.discoswap.data.message

import com.example.discoswap.model.message.Message
import com.example.discoswap.network.message.MessageApiService
import com.example.discoswap.network.message.asDomainObject
import com.example.discoswap.network.message.getMessageDetailsAsFlow
import com.example.discoswap.network.message.getMessagesAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Repository interface for handling message operations.
 */
interface MessageRepository {
    /**
     * Insert a message into the repository.
     *
     * @param message the message to insert
     */
    suspend fun insert(message: Message)

    /**
     * Get the list of messages as a flow.
     *
     * @return a Flow emitting the list of messages
     */
    fun getMessages(): Flow<List<Message>>

    /**
     * Get details of a specific message as a flow.
     *
     * @param id the ID of the message
     * @return a Flow emitting the details of the message with the given ID
     */
    fun getMessageDetails(id: String): Flow<Message>

    /**
     * Refresh the messages by fetching data from the remote service and updating the local repository.
     */
    suspend fun refresh()

    /**
     * Load details of a specific message by fetching data from the remote service and updating the local repository.
     *
     * @param id the ID of the message
     */
    suspend fun loadMessageDetails(id: String)
}

/**
 * Implementation of [MessageRepository] with caching capabilities.
 *
 * @property messageDao the DAO for accessing local database
 * @property messageApiService the remote message API service
 */
class CachingMessageRepository(
    private val messageDao: MessageDao,
    private val messageApiService: MessageApiService
) : MessageRepository {
    /**
     * Insert a message into the local repository.
     *
     * @param message the message to insert
     */
    override suspend fun insert(message: Message) {
        messageDao.insert(message.asDbMessage())
    }

    /**
     * Get the list of messages as a flow, with automatic refresh if the list is empty.
     *
     * @return a Flow emitting the list of messages
     */
    override fun getMessages(): Flow<List<Message>> {
        return messageDao.getMessages().map { it.asDomainMessages() }
            .onEach { messages ->
                if (messages.isEmpty()) {
                    refresh()
                }
            }
    }

    /**
     * Get details of a specific message as a flow, with loading details if not already present.
     *
     * @param id the ID of the message
     * @return a Flow emitting the details of the message with the given ID
     */
    override fun getMessageDetails(id: String): Flow<Message> {
        return messageDao.getMessageDetails(id).map { it.asDomainMessage() }
            .onEach { message ->
                if (message.text == null) {
                    loadMessageDetails(id)
                }
            }
    }

    /**
     * Refresh the messages by fetching data from the remote service and updating the local repository.
     */
    override suspend fun refresh() {
        messageApiService.getMessagesAsFlow().collect {
            for (message in it.items) {
                insert(message.asDomainObject())
            }
        }
    }

    /**
     * Load details of a specific message by fetching data from the remote service and updating the local repository.
     *
     * @param id the ID of the message
     */
    override suspend fun loadMessageDetails(id: String) {
        messageApiService.getMessageDetailsAsFlow(id).collect {
            insert(it.asDomainObject())
        }
    }
}
