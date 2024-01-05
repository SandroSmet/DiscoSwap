package com.example.discoswap.network.order

import com.example.discoswap.model.inventory.Item
import com.example.discoswap.model.order.Order
import com.example.discoswap.model.order.Price
import com.example.discoswap.model.inventory.Release
import com.example.discoswap.model.order.Status
import com.example.discoswap.network.message.ApiUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents detailed information about an order from the remote API.
 *
 * @property id the unique identifier of the order
 * @property buyer the buyer information associated with the order
 * @property status the status of the order
 * @property total the total price of the order
 * @property items the list of items included in the order
 */
@Serializable
data class ApiOrderDetail(
    val id: String,
    val buyer: ApiUser,
    val status: String,
    val total: ApiValue,
    val items: List<ApiOrderItemDetail>,
)

/**
 * Represents detailed information about an order item from the remote API.
 *
 * @property id the unique identifier of the order item
 * @property price the price of the order item
 * @property mediaCondition the media condition of the order item
 * @property sleeveCondition the sleeve condition of the order item
 * @property conditionComments comments related to the condition of the order item
 * @property itemLocation the location of the order item
 * @property privateComments private comments related to the order item
 * @property release the release information associated with the order item
 */
@Serializable
data class ApiOrderItemDetail(
    val id: Long,
    val price: ApiValue,
    @SerialName("media_condition")
    val mediaCondition: String,
    @SerialName("sleeve_condition")
    val sleeveCondition: String,
    @SerialName("condition_comments")
    val conditionComments: String,
    @SerialName("item_location")
    val itemLocation: String,
    @SerialName("private_comments")
    val privateComments: String,
    val release: ApiRelease,
)

/**
 * Represents release information from the remote API.
 *
 * @property id the unique identifier of the release
 * @property title the title of the release
 * @property description the description of the release
 * @property artist the artist associated with the release
 * @property thumbnail the thumbnail image URL of the release
 * @property format the format of the release
 */
@Serializable
data class ApiRelease(
    val id: Int,
    val title: String,
    val description: String,
    val artist: String,
    val thumbnail: String,
    val format: String,
)

/**
 * Represents a list of orders from the remote API.
 *
 * @property orders the list of orders
 */
@Serializable
data class ApiOrders(
    val orders: List<ApiOrderDetail>,
)

/**
 * Represents basic information about an order from the remote API.
 *
 * @property id the unique identifier of the order
 * @property buyer the buyer information associated with the order
 * @property status the status of the order
 * @property total the total price of the order
 */
@Serializable
data class ApiOrderInfo(
    val id: String,
    val buyer: ApiUser,
    val status: String,
    val total: ApiValue,
)

/**
 * Represents the price information from the remote API.
 *
 * @property value the numerical value of the price
 * @property currency the currency associated with the price
 */
@Serializable
data class ApiValue(
    val value: Double,
    val currency: String,
)

/**
 * Extension function to convert an [ApiOrderDetail] to a [Order] in the domain layer.
 *
 * @receiver the [ApiOrderDetail] to be converted
 * @return the corresponding [Order] in the domain layer
 */
fun ApiOrderDetail.asDomainObject() =
    Order(
        id = this.id,
        buyer = this.buyer.username,
        total = this.total.asDomainObject(),
        status = when (this.status) {
            "New Order" -> Status.NewOrder
            "Buyer Contacted" -> Status.BuyerContacted
            "Invoice Sent" -> Status.InvoiceSent
            "Payment Pending" -> Status.PaymentPending
            "Payment Received" -> Status.PaymentReceived
            "In Progress" -> Status.InProgress
            "Shipped" -> Status.Shipped
            "Refund Sent" -> Status.RefundSent
            "Cancelled (Non-Paying Buyer)" -> Status.CancelledNonPayingBuyer
            "Cancelled (Item Unavailable)" -> Status.CancelledItemUnavailable
            "Cancelled (Per Buyer's Request)" -> Status.CancelledBuyerRequest
            "Merged" -> Status.Merged
            else -> Status.NewOrder
        },
        items = this.items.map {
            Item(
                id = it.id,
                price = it.price.asDomainObject(),
                mediaCondition = it.mediaCondition,
                sleeveCondition = it.sleeveCondition,
                conditionComments = it.conditionComments,
                itemLocation = it.itemLocation,
                privateComments = it.privateComments,
                release = it.release.asDomainObject(),
            )
        },
    )

/**
 * Extension function to convert an [ApiValue] to a [Price] in the domain layer.
 *
 * @receiver the [ApiValue] to be converted
 * @return the corresponding [Price] in the domain layer
 */
fun ApiValue.asDomainObject() =
    Price(
        value = this.value,
        currency = this.currency,
    )

/**
 * Extension function to convert an [ApiRelease] to a [Release] in the domain layer.
 *
 * @receiver the [ApiRelease] to be converted
 * @return the corresponding [Release] in the domain layer
 */
fun ApiRelease.asDomainObject() =
    Release(
        id = this.id,
        title = this.title,
        description = this.description,
        artist = this.artist,
        format = this.format,
        thumbnail = this.thumbnail,
    )

/**
 * Extension function to convert an [ApiOrderItemDetail] to an [Item] in the domain layer.
 *
 * @receiver the [ApiOrderItemDetail] to be converted
 * @param orderId the unique identifier of the associated order
 * @return the corresponding [Item] in the domain layer
 */
fun ApiOrderItemDetail.asDomainObject(orderId: String) =
    Item(
        id = this.id,
        price = this.price.asDomainObject(),
        mediaCondition = this.mediaCondition,
        sleeveCondition = this.sleeveCondition,
        conditionComments = this.conditionComments,
        itemLocation = this.itemLocation,
        privateComments = this.privateComments,
        release = this.release.asDomainObject(),
        orderId = orderId,
    )
