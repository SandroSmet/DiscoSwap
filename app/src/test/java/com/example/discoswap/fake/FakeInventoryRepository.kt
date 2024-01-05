package com.example.discoswap.fake

import com.example.discoswap.data.inventory.InventoryRepository
import com.example.discoswap.data.inventory.asDbItem
import com.example.discoswap.model.inventory.Item
import com.example.discoswap.network.inventory.asDomainObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeInventoryRepository : InventoryRepository {
    override suspend fun insert(item: Item) {
        FakeDataSource.inventory.plus(item.asDbItem())
    }

    override suspend fun getInventory(): Flow<List<Item>> = flow {
        emit(FakeDataSource.inventory.map { it.asDomainObject() })
    }

    override suspend fun getInventoryItemDetails(inventoryItemId: String): Flow<Item> = flow {
        emit(FakeDataSource.inventory.find { it.id == inventoryItemId.toLong() }!!.asDomainObject())
    }

    override suspend fun refresh() {
        // Not needed
    }

}