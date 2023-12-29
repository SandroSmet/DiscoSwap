package com.example.discoswap.ui.inventory

import com.example.discoswap.model.inventory.Item

sealed interface InventoryApiState {
    object Error : InventoryApiState
    object Loading : InventoryApiState
    data class Success(val inventoryItems: List<Item>) : InventoryApiState
}

sealed interface InventoryItemDetailApiState {
    object Error : InventoryItemDetailApiState
    object Loading : InventoryItemDetailApiState
    data class Success(val inventoryItem: Item) : InventoryItemDetailApiState
}
