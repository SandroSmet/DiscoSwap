package com.example.discoswap.network.inventory

import com.example.discoswap.network.message.MessageApiService
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Path

interface InventoryApiService {
    @GET("users/Born4Vinyl/inventory?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi&sort=listed&sort_order=desc&per_page=100&status=For Sale")
    suspend fun getInventory(): ApiInventory

    @GET("marketplace/listings/{id}?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi")
    suspend fun getInventoryItemDetails(@Path("id") id: String): ApiInventoryItem

}

fun InventoryApiService.getInventoryAsFlow() = flow { emit(getInventory()) }
