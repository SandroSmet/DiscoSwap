package com.example.discoswap.ui.inventory.inventoryoverview

import com.example.discoswap.model.inventory.Item

data class InventoryOverviewState(
    val currentInventoryItemList: List<Item> = emptyList(),
)
