package com.example.discoswap.network.inventory

import com.example.discoswap.model.inventory.Item
import com.example.discoswap.network.order.ApiRelease
import com.example.discoswap.network.order.ApiValue
import com.example.discoswap.network.order.asDomainObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the response structure for inventory data from the remote API.
 *
 * @property listings the list of inventory items
 */
@Serializable
data class ApiInventory(
    val listings: List<ApiInventoryItem>,
)

/**
 * Represents an individual inventory item in the remote API response.
 *
 * @property id the unique identifier of the inventory item
 * @property condition the media condition of the inventory item
 * @property sleeveCondition the sleeve condition of the inventory item
 * @property comments additional comments about the inventory item's condition
 * @property location the location of the inventory item
 * @property price the price details of the inventory item
 * @property release the release details associated with the inventory item
 */
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

/**
 * Extension function to convert an [ApiInventoryItem] to a [Item] in the domain layer.
 *
 * @receiver the [ApiInventoryItem] to be converted
 * @return the corresponding [Item] in the domain layer
 */
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