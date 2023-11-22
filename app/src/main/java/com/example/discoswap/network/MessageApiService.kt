package com.example.discoswap.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

interface MessageApiService {
    @GET("messages?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi")
    suspend fun getMessages(): ApiMessages
}

private var retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(
        Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()),
    )
    .baseUrl("https://api.discogs.com")
    .build()

object MessageApi {
    val messageService: MessageApiService by lazy {
        retrofit.create(MessageApiService::class.java)
    }
}
