package com.example.discoswap.network

import com.example.discoswap.model.orders.Order
import com.example.discoswap.model.orders.Status
import kotlinx.serialization.Serializable

@Serializable
data class ApiOrders(
    val orders: List<ApiOrderItem>,
)

@Serializable
data class ApiOrderItem(
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

fun ApiOrderItem.asDomainObject() =
    Order(
        id = this.id,
        buyer = this.buyer.username,
        status = when (this.status) {
            "NewOrder" -> Status.NewOrder
            "BuyerContacted" -> Status.BuyerContacted
            "InvoiceSent" -> Status.InvoiceSent
            "PaymentPending" -> Status.PaymentPending
            "PaymentReceived" -> Status.PaymentReceived
            "InProgress" -> Status.InProgress
            "Shipped" -> Status.Shipped
            "RefundSent" -> Status.RefundSent
            "CancelledNonPayingBuyer" -> Status.CancelledNonPayingBuyer
            "CancelledItemUnavailable" -> Status.CancelledItemUnavailable
            "CancelledBuyerRequest" -> Status.CancelledBuyerRequest
            else -> Status.NewOrder
        },
        items = listOf(),
    )

fun List<ApiOrderItem>.asDomainObjects() =
    map {
        Order(
            id = it.id,
            buyer = it.buyer.username,
            status = when (it.status) {
                "NewOrder" -> Status.NewOrder
                "BuyerContacted" -> Status.BuyerContacted
                "InvoiceSent" -> Status.InvoiceSent
                "PaymentPending" -> Status.PaymentPending
                "PaymentReceived" -> Status.PaymentReceived
                "InProgress" -> Status.InProgress
                "Shipped" -> Status.Shipped
                "RefundSent" -> Status.RefundSent
                "CancelledNonPayingBuyer" -> Status.CancelledNonPayingBuyer
                "CancelledItemUnavailable" -> Status.CancelledItemUnavailable
                "CancelledBuyerRequest" -> Status.CancelledBuyerRequest
                else -> Status.NewOrder
            },
            items = listOf(),
        )
    }
