package com.example.discoswap.data.order

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.discoswap.data.inventory.DbItem
import com.example.discoswap.data.inventory.asDomainPrice
import com.example.discoswap.model.inventory.Item
import com.example.discoswap.model.order.Order
import com.example.discoswap.model.order.Status

@Entity(tableName = "orders")
data class DbOrder (
    @PrimaryKey
    var id: String,
    var buyer: String,
    var status: String,
    var total: Double,
)

data class DbOrderWithItems(
    @Embedded
    val order: DbOrder,

    @Relation(
        parentColumn = "id",
        entityColumn = "orderId",
    )
    val items: List<DbItem>,
)


fun DbOrder.asDomainOrder(items: List<Item> = emptyList()) = Order(
    id = id,
    buyer = buyer,
    status = status.asDomainOrderStatus(),
    total = total.asDomainPrice(),
    items = items,
)

fun String.asDomainOrderStatus() = when (this) {
    "New Order" -> Status.NewOrder
    "Buyer Contacted" -> Status.BuyerContacted
    "Invoice Sent" -> Status.InvoiceSent
    "Payment Pending" -> Status.PaymentPending
    "Payment Received" -> Status.PaymentReceived
    "In Progress" -> Status.InProgress
    "Shipped" -> Status.Shipped
    "Refund Sent" -> Status.RefundSent
    "Cancelled - Non-Paying Buyer" -> Status.CancelledNonPayingBuyer
    "Cancelled - Item Unavailable" -> Status.CancelledItemUnavailable
    "Cancelled - Buyer Request" -> Status.CancelledBuyerRequest
    "Merged" -> Status.Merged
    else -> Status.NewOrder
}

fun Status.asDbOrderStatus() = when (this) {
    Status.NewOrder -> "New Order"
    Status.BuyerContacted -> "Buyer Contacted"
    Status.InvoiceSent -> "Invoice Sent"
    Status.PaymentPending -> "Payment Pending"
    Status.PaymentReceived -> "Payment Received"
    Status.InProgress -> "In Progress"
    Status.Shipped -> "Shipped"
    Status.RefundSent -> "Refund Sent"
    Status.CancelledNonPayingBuyer -> "Cancelled - Non-Paying Buyer"
    Status.CancelledItemUnavailable -> "Cancelled - Item Unavailable"
    Status.CancelledBuyerRequest -> "Cancelled - Buyer Request"
    Status.Merged -> "Merged"
}

fun Order.asDbOrder() = DbOrder(
    id = id,
    buyer = buyer,
    status = status.asDbOrderStatus(),
    total = total.value,
)