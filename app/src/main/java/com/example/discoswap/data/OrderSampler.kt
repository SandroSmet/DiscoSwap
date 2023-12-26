package com.example.discoswap.data

import com.example.discoswap.model.orders.Item
import com.example.discoswap.model.orders.Order
import com.example.discoswap.model.orders.Price
import com.example.discoswap.model.orders.Release
import com.example.discoswap.model.orders.Status

object OrderSampler {
    val releases = mutableListOf<Release>(
        Release(
            id = "123",
            description = "Great LP from John in 1988",
            artist = "John",
            format = "LP",
            title = "Some song",
        ),
        Release(
            id = "1234",
            description = "Great 12\" from James in 1985",
            artist = "James",
            format = "12",
            title = "Another song",
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
            id = "123",
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
            status = Status.InvoiceSent,
            items = listOf(items[0]),
        ),
    )

    val getAll: () -> MutableList<Order> = {
        orders
    }
}
