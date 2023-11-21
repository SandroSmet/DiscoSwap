package com.example.discoswap.model.orders

data class Item(
    var id: String,
    var price: Price,
    var mediaCondition: String,
    var sleeveCondition: String,
    var conditionComments: String,
    var itemLocation: String,
    var privateComments: String,
    var release: Release,
)
