package com.example.discoswap.network.inventory

import com.example.discoswap.model.inventory.Item
import com.example.discoswap.network.order.ApiRelease
import com.example.discoswap.network.order.ApiValue
import com.example.discoswap.network.order.asDomainObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiInventory(
    val listings: List<ApiInventoryItem>,
)

@Serializable
data class ApiInventoryItem(
    val id: Long,
    val condition: String,
    @SerialName("sleeve_condition")
    val sleeveCondition: String,
    val comments: String,
    val location: String,
    val price: ApiValue,
    val release: ApiRelease,
    )

fun ApiInventoryItem.asDomainObject() = Item(
    id = this.id,
    price = this.price.asDomainObject(),
    mediaCondition = this.condition,
    sleeveCondition = this.sleeveCondition,
    conditionComments = this.comments,
    itemLocation = this.location,
    privateComments = "",
    release = this.release.asDomainObject(),
)

fun List<ApiInventoryItem>.asDomainObjects() =
    map {
        Item(
            id = it.id,
            price = it.price.asDomainObject(),
            mediaCondition = it.condition,
            sleeveCondition = it.sleeveCondition,
            conditionComments = it.comments,
            itemLocation = it.location,
            privateComments = "",
            release = it.release.asDomainObject(),
        )
    }