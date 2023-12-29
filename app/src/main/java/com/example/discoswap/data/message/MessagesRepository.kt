package com.example.discoswap.data.message

import com.example.discoswap.model.message.Message
import com.example.discoswap.network.message.MessageApiService
import com.example.discoswap.network.message.asDomainObject
import com.example.discoswap.network.message.asDomainObjects

interface MessagesRepository {
    suspend fun getMessages(): List<Message>
    suspend fun getMessageDetails(messageId: String): Message

}

class ApiMessagesRepository(
    private val messagesApiService: MessageApiService
): MessagesRepository {
    override suspend fun getMessages(): List<Message> {
        return messagesApiService.getMessages().items.asDomainObjects()
    }

    override suspend fun getMessageDetails(messageId: String): Message {
        return messagesApiService.getMessageDetails(messageId).asDomainObject()
    }

}