package com.example.discoswap.fake

import com.example.discoswap.data.message.MessageRepository
import com.example.discoswap.model.message.Message
import com.example.discoswap.network.message.asDomainObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMessageRepository : MessageRepository {
    override suspend fun insert(message: Message) {
        FakeDataSource.messages.plus(message)
    }

    override fun getMessages(): Flow<List<Message>> = flow {
        emit(FakeDataSource.messages.map { it.asDomainObject() })
    }

    override fun getMessageDetails(id: String): Flow<Message> = flow {
        emit(FakeDataSource.messages.find { it.id == id }!!.asDomainObject())
    }

    override suspend fun refresh() {
        // Not needed
    }

    override suspend fun loadMessageDetails(id: String) {
        // Not needed
    }
}