package com.example.discoswap.network.order

import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

interface OrderApiService {
    @GET("marketplace/orders?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi&sort=last_activity&sort_order=desc&per_page=50")
    suspend fun getOrders(): ApiOrders
}

fun OrderApiService.getOrdersAsFlow() = flow { emit(getOrders()) }