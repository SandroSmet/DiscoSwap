package com.example.discoswap.network.inventory

import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

/**
 * Retrofit service interface for interacting with the inventory-related endpoints of the remote API.
 */
interface InventoryApiService {
    /**
     * Fetches the user's inventory from the remote API.
     *
     * @return an [ApiInventory] representing the user's inventory
     */
    @GET("users/Born4Vinyl/inventory?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi&sort=listed&sort_order=desc&per_page=50&status=For Sale")
    suspend fun getInventory(): ApiInventory
}

/**
 * Extension function to convert the [getInventory] suspend function to a flow.
 *
 * @receiver the [InventoryApiService] instance
 * @return a Flow emitting the result of [getInventory]
 */
fun InventoryApiService.getInventoryAsFlow() = flow { emit(getInventory()) }
