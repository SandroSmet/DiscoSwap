package com.example.discoswap.data

import android.content.Context
import androidx.room.Room
import com.example.discoswap.data.database.MessageDao
import com.example.discoswap.data.database.MessageDatabase
import com.example.discoswap.data.inventory.ApiInventoryRepository
import com.example.discoswap.data.inventory.InventoryRepository
import com.example.discoswap.data.message.CachingMessageRepository
import com.example.discoswap.data.order.ApiOrderRepository
import com.example.discoswap.data.message.MessageRepository
import com.example.discoswap.data.order.OrderRepository
import com.example.discoswap.network.inventory.InventoryApiService
import com.example.discoswap.network.message.MessageApiService
import com.example.discoswap.network.order.OrderApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val messageRepository: MessageRepository
    val orderRepository: OrderRepository
    val inventoryRepository: InventoryRepository
}

@OptIn(ExperimentalSerializationApi::class)
private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

class DefaultAppContainer(
    private val applicationContext: Context
) : AppContainer{

    private val baseUrl = "https://api.discogs.com"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val messageDao: MessageDao by lazy {
        Room.databaseBuilder(
            applicationContext,
            MessageDatabase::class.java,
            "message_database"
            ).build().messageDao()
    }

    override val messageRepository: MessageRepository by lazy {
        CachingMessageRepository(
            messageDao = messageDao,
            messageApiService = retrofit.create(MessageApiService::class.java)
        )
    }

    override val orderRepository: OrderRepository by lazy {
        ApiOrderRepository(retrofit.create(OrderApiService::class.java))
    }

    override val inventoryRepository: InventoryRepository by lazy {
        ApiInventoryRepository(retrofit.create(InventoryApiService::class.java))
    }

}