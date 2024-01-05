package com.example.discoswap.data.inventory

import com.example.discoswap.model.inventory.Item
import com.example.discoswap.network.inventory.InventoryApiService
import com.example.discoswap.network.inventory.asDomainObject
import com.example.discoswap.network.inventory.getInventoryAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
/**
 * Repository interface for handling inventory operations.
 */
interface InventoryRepository {
    /**
     * Insert an item into the database
     *
     * @param item the item to insert
     */
    suspend fun insert(item: Item)

    /**
     * Get the list of inventory items as a flow.
     *
     * @return a Flow emitting the list of inventory items
     */
    suspend fun getInventory(): Flow<List<Item>>

    /**
     * Get details of a specific inventory item.
     *
     * @param inventoryItemId the ID of the inventory item
     * @return a Flow emitting the details of the inventory item
     */
    suspend fun getInventoryItemDetails(inventoryItemId: String): Flow<Item>

    /**
     * Refresh the inventory by fetching data from the remote service and updating the local database.
     */
    suspend fun refresh()
}

/**
 * Implementation of [InventoryRepository] with caching capabilities.
 *
 * @property itemDao the DAO for accessing local database
 * @property inventoryApiService the remote inventory API service
 */
class CachingInventoryRepository(
    private val itemDao: ItemDao,
    private val inventoryApiService: InventoryApiService
) : InventoryRepository {
    /**
     * Insert an item into the local database.
     *
     * @param item the item to insert
     */
    override suspend fun insert(item: Item) {
        itemDao.insert(item.asDbItem())
    }

    /**
     * Get the list of inventory items as a flow, with automatic refresh if the list is empty.
     *
     * @return a Flow emitting the list of inventory items
     */
    override suspend fun getInventory(): Flow<List<Item>> {
        return itemDao.getInventory().map { it.asDomainInventoryItems() }
            .onEach { items ->
                if (items.isEmpty()) {
                    refresh()
                }
            }
    }

    /**
     * Get details of a specific inventory item as a flow.
     *
     * @param inventoryItemId the ID of the inventory item
     * @return a Flow emitting the details of the inventory item
     */
    override suspend fun getInventoryItemDetails(inventoryItemId: String): Flow<Item> {
        return itemDao.getInventoryItemDetails(inventoryItemId).map { it.asDomainInventoryItems() }
    }

    /**
     * Refresh the inventory by fetching data from the remote service and updating the local database.
     */
    override suspend fun refresh() {
        inventoryApiService.getInventoryAsFlow().collect {
            for (item in it.listings) {
                insert(item.asDomainObject())
            }
        }
    }
}
