package com.example.discoswap.network.message

import retrofit2.http.GET
import retrofit2.http.Path

interface MessageApiService {
    @GET("messages?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi&per_page=100")
    suspend fun getMessages(): ApiMessages

    @GET("messages/{id}?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi")
    suspend fun getMessageDetails(@Path("id") id: String): ApiMessageItem

}
