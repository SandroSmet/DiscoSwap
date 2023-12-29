package com.example.discoswap.data

import com.example.discoswap.data.inventory.ApiInventoryRepository
import com.example.discoswap.data.inventory.InventoryRepository
import com.example.discoswap.data.message.ApiMessagesRepository
import com.example.discoswap.data.order.ApiOrdersRepository
import com.example.discoswap.data.message.MessagesRepository
import com.example.discoswap.data.order.OrdersRepository
import com.example.discoswap.network.inventory.InventoryApiService
import com.example.discoswap.network.message.MessageApiService
import com.example.discoswap.network.order.OrderApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val messagesRepository: MessagesRepository
    val ordersRepository: OrdersRepository
    val inventoryRepository: InventoryRepository
}

@OptIn(ExperimentalSerializationApi::class)
private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

class DefaultAppContainer : AppContainer{

    private val baseUrl = "https://api.discogs.com"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : MessageApiService by lazy {
        retrofit.create(MessageApiService::class.java)
    }

    private val retrofitService2: OrderApiService by lazy {
        retrofit.create(OrderApiService::class.java)
    }

    private val retrofitService3: InventoryApiService by lazy {
        retrofit.create(InventoryApiService::class.java)
    }

    override val messagesRepository: MessagesRepository by lazy {
        ApiMessagesRepository(retrofitService)
    }

    override val ordersRepository: OrdersRepository by lazy {
        ApiOrdersRepository(retrofitService2)
    }
    override val inventoryRepository: InventoryRepository by lazy {
        ApiInventoryRepository(retrofitService3)
    }

}