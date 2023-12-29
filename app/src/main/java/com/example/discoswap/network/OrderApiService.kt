package com.example.discoswap.network

import retrofit2.http.GET
import retrofit2.http.Path

interface OrderApiService {
    @GET("marketplace/orders?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi&sort=last_activity&sort_order=desc&per_page=100")
    suspend fun getOrders(): ApiOrders

    @GET("marketplace/orders/{id}?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi")
    suspend fun getOrderDetails(@Path("id") id: String): ApiOrderDetail
}