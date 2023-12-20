package com.example.discoswap.network

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface MessageApiService {
    @GET("messages?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi")
    suspend fun getMessages(): ApiMessages

    @GET("messages/{id}?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi")
    suspend fun getMessageDetails(@Path("id") id: String): ApiMessageItem

}
