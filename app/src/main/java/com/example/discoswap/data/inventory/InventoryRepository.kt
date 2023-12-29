package com.example.discoswap.data.inventory

import com.example.discoswap.model.inventory.Item
import com.example.discoswap.network.inventory.InventoryApiService
import com.example.discoswap.network.inventory.asDomainObject
import com.example.discoswap.network.inventory.asDomainObjects

interface InventoryRepository {
    suspend fun getInventory(): List<Item>
    suspend fun getInventoryItemDetails(inventoryItemId: String): Item

}

class ApiInventoryRepository(
    private val inventoryApiService: InventoryApiService
): InventoryRepository {
    override suspend fun getInventory(): List<Item> {
        return inventoryApiService.getInventory().listings.asDomainObjects()
    }

    override suspend fun getInventoryItemDetails(inventoryItemId: String): Item {
        return inventoryApiService.getInventoryItemDetails(inventoryItemId).asDomainObject()
    }

}