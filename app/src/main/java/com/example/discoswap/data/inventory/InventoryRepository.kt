package com.example.discoswap.data.inventory

import com.example.discoswap.model.inventory.Item
import com.example.discoswap.network.inventory.InventoryApiService
import com.example.discoswap.network.inventory.asDomainObject
import com.example.discoswap.network.inventory.getInventoryAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface InventoryRepository {
    suspend fun insert(item: Item)
    suspend fun getInventory(): Flow<List<Item>>
    suspend fun getInventoryItemDetails(inventoryItemId: String): Flow<Item>
    suspend fun refresh()

}

class CachingInventoryRepository(
    private val itemDao: ItemDao,
    private val inventoryApiService: InventoryApiService
): InventoryRepository {
    override suspend fun insert(item: Item) {
        itemDao.insert(item.asDbItem())
    }

    override suspend fun getInventory(): Flow<List<Item>> {
        return itemDao.getInventory().map { it.asDomainInventoryItems() }
            .onEach { items ->
                if (items.isEmpty()) {
                    refresh()
                }
            }
    }

    override suspend fun getInventoryItemDetails(inventoryItemId: String): Flow<Item> {
        return itemDao.getInventoryItemDetails(inventoryItemId).map { it.asDomainInventoryItems() }
    }

    override suspend fun refresh() {
        inventoryApiService.getInventoryAsFlow().collect {
            for (item in it.listings) {
                insert(item.asDomainObject())
            }
        }
    }
}