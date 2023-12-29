package com.example.discoswap.network

import com.example.discoswap.model.orders.Item
import com.example.discoswap.model.orders.Order
import com.example.discoswap.model.orders.Price
import com.example.discoswap.model.orders.Release
import com.example.discoswap.model.orders.Status
import kotlinx.serialization.Serializable

@Serializable
data class ApiOrderDetail(
    val id: String,
    val buyer: ApiUser,
    val status: String,
    val total: ApiValue,
    val items: List<ApiOrderItemDetail>,
)

@Serializable
data class ApiOrderItemDetail(
    val id: Long,
    val price: ApiValue,
    val media_condition: String,
    val sleeve_condition: String,
    val condition_comments: String,
    val item_location: String,
    val private_comments: String,
    val release: ApiRelease,
)

@Serializable
data class ApiRelease(
    val id: Int,
    val title: String,
    val description: String,
    val artist: String,
    val thumbnail: String,
    val format: String,
)

@Serializable
data class ApiOrders(
    val orders: List<ApiOrderInfo>,
)

@Serializable
data class ApiOrderInfo(
    val id: String,
    val buyer: ApiUser,
    val status: String,
    val total: ApiValue,
)

@Serializable
data class ApiValue(
    val value: Double,
    val currency: String,
)

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
            else -> Status.NewOrder
        },
        items = this.items.map {
            Item(
                id = it.id,
                price = it.price.asDomainObject(),
                mediaCondition = it.media_condition,
                sleeveCondition = it.sleeve_condition,
                conditionComments = it.condition_comments,
                itemLocation = it.item_location,
                privateComments = it.private_comments,
                release = Release(
                    id = it.release.id,
                    title = it.release.title,
                    description = it.release.description,
                    artist = it.release.artist,
                    format = it.release.format,
                    thumbnail = it.release.thumbnail,
                ),
            )
        },
    )

fun List<ApiOrderInfo>.asDomainObjects() =
    map {
        Order(
            id = it.id,
            buyer = it.buyer.username,
            total = it.total.asDomainObject(),
            status = when (it.status) {
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
                else -> Status.NewOrder
            },
            items = listOf(),
        )
    }

fun ApiValue.asDomainObject() =
    Price(
        value = this.value,
        currency = this.currency,
    )