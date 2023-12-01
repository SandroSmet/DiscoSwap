package com.example.discoswap.data

import com.example.discoswap.model.messages.Message
import com.example.discoswap.network.MessageApiService
import com.example.discoswap.network.asDomainObject
import com.example.discoswap.network.asDomainObjects

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