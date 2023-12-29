package com.example.discoswap.data.order

import com.example.discoswap.model.inventory.Item
import com.example.discoswap.model.order.Order
import com.example.discoswap.model.order.Price
import com.example.discoswap.model.inventory.Release
import com.example.discoswap.model.order.Status

object OrderSampler {
    val releases = mutableListOf<Release>(
        Release(
            id = 123,
            description = "Great LP from John in 1988",
            artist = "John",
            format = "LP",
            title = "Some song",
            thumbnail = "a link",
        ),
        Release(
            id = 1234,
            description = "Great 12\" from James in 1985",
            artist = "James",
            format = "12",
            title = "Another song",
            thumbnail = "another link",
        ),
    )
    val prices = mutableListOf<Price>(
        Price(
            value = 15.50,
            currency = "EUR",
        ),
        Price(
            value = 25.25,
            currency = "EUR",
        ),
        Price(
            value = 9.99,
            currency = "EUR",
        ),
    )
    val items = mutableListOf<Item>(
        Item(
            id = 123,
            price = prices[0],
            itemLocation = "50-5",
            conditionComments = "good",
            mediaCondition = "VG+",
            sleeveCondition = "VG+",
            privateComments = "",
            release = releases[0],
        ),
    )
    val orders = mutableListOf<Order>(
        Order(
            id = "1",
            buyer = "Jan",
            total = prices[0],
            status = Status.InvoiceSent,
            items = listOf(items[0]),
        ),
    )

    val getAll: () -> MutableList<Order> = {
        orders
    }
}
