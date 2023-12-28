package com.example.discoswap.data

import com.example.discoswap.network.MessageApiService
import com.example.discoswap.network.OrderApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val messagesRepository: MessagesRepository
    val ordersRepository: OrdersRepository
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

    override val messagesRepository: MessagesRepository by lazy {
        ApiMessagesRepository(retrofitService)
    }

    override val ordersRepository: OrdersRepository by lazy {
        ApiOrdersRepository(retrofitService2)
    }

}