package com.example.discoswap.ui.inventory

import com.example.discoswap.model.inventory.Item

sealed interface InventoryApiState {
    object Error : InventoryApiState
    object Loading : InventoryApiState
    object Success : InventoryApiState
}

sealed interface InventoryItemDetailApiState {
    object Error : InventoryItemDetailApiState
    object Loading : InventoryItemDetailApiState
    object Success : InventoryItemDetailApiState
}
