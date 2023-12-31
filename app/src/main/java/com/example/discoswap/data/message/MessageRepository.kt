package com.example.discoswap.data.message

import com.example.discoswap.model.message.Message
import com.example.discoswap.network.message.MessageApiService
import com.example.discoswap.network.message.asDomainObject
import com.example.discoswap.network.message.getMessageDetailsAsFlow
import com.example.discoswap.network.message.getMessagesAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface MessageRepository {
    suspend fun insert(message: Message)

    fun getMessages(): Flow<List<Message>>

    fun getMessageDetails(id: String): Flow<Message>

    suspend fun refresh()

    suspend fun loadMessageDetails(id: String)

}

class CachingMessageRepository(
    private val messageDao: MessageDao,
    private val messageApiService: MessageApiService
): MessageRepository {
    override suspend fun insert(message: Message) {
        messageDao.insert(message.asDbMessage())
    }

    override fun getMessages(): Flow<List<Message>> {
        return messageDao.getMessages().map { it.asDomainMessages() }
            .onEach { messages ->
                if (messages.isEmpty()) {
                    refresh()
                }
            }
    }

    override fun getMessageDetails(id: String): Flow<Message> {
        return messageDao.getMessageDetails(id).map { it.asDomainMessage() }
            .onEach { message ->
                if (message.text == null) {
                    loadMessageDetails(id)
                }
            }
    }

    override suspend fun refresh() {
        messageApiService.getMessagesAsFlow().collect {
            for (message in it.items) {
                insert(message.asDomainObject())
            }
        }
    }

    override suspend fun loadMessageDetails(id: String) {
        messageApiService.getMessageDetailsAsFlow(id).collect {
            insert(it.asDomainObject())
        }
    }
}
