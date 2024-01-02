package com.example.discoswap.model.inventory

import com.example.discoswap.model.order.Price

data class Item(
    var id: Long,
    var orderId: String = "0",
    var price: Price,
    var mediaCondition: String,
    var sleeveCondition: String,
    var conditionComments: String,
    var itemLocation: String,
    var privateComments: String,
    var release: Release,
)
