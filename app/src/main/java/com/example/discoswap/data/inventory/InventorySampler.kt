package com.example.discoswap.data.inventory

import com.example.discoswap.data.order.OrderSampler.prices
import com.example.discoswap.model.inventory.Item
import com.example.discoswap.model.inventory.Release

object InventorySampler {
    private val releases = mutableListOf<Release>(
        Release(
            id = 1,
            title = "Test release",
            artist = "Test artist",
            description = "Test description",
            format = "LP",
            thumbnail = "some link",
        ),
        Release(
            id = 2,
            title = "Another test release",
            artist = "Another test artist",
            description = "Another test description",
            format = "12\"",
            thumbnail = "another link",
        ),
    )


    private val inventoryItems = mutableListOf<Item>(
        Item(
            id = 1,
            price = prices[0],
            mediaCondition = "Mint (M)",
            sleeveCondition = "Mint (M)",
            conditionComments = "Great condition",
            itemLocation = "50-5",
            privateComments = "Some private comments",
            release = releases[0],
        ),
        Item(
            id = 2,
            price = prices[1],
            mediaCondition = "Very Good Plus (VG+)",
            sleeveCondition = "Very Good Plus (VG+)",
            conditionComments = "Great condition",
            itemLocation = "50-5",
            privateComments = "Some private comments",
            release = releases[1],
        ),
    )

    val getAll: () -> MutableList<Item> = {
        inventoryItems
    }
}
