package com.example.discoswap.data

import com.example.discoswap.network.MessageApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val messagesRepository: MessagesRepository
}

class DefaultAppContainer(): AppContainer{

    private val baseUrl = "https://api.discogs.com"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : MessageApiService by lazy {
        retrofit.create(MessageApiService::class.java)
    }

    override val messagesRepository: MessagesRepository by lazy {
        ApiMessagesRepository(retrofitService)
    }

}