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

/**
 * Database entity representing an order.
 *
 * @property id the unique identifier of the order
 * @property buyer the buyer associated with the order
 * @property status the status of the order
 * @property total the total cost of the order
 */
@Entity(tableName = "orders")
data class DbOrder(
    @PrimaryKey
    var id: String,
    var buyer: String,
    var status: String,
    var total: Double,
)

/**
 * Data class representing an order with its associated items.
 *
 * @property order the order details
 * @property items the list of items associated with the order
 */
data class DbOrderWithItems(
    @Embedded
    val order: DbOrder,

    @Relation(
        parentColumn = "id",
        entityColumn = "orderId",
    )
    val items: List<DbItem>,
)

/**
 * Extension function to convert a [DbOrder] to a [Order] in the domain layer.
 *
 * @receiver the [DbOrder] to be converted
 * @param items the list of items associated with the order
 * @return the corresponding [Order] in the domain layer
 */
fun DbOrder.asDomainOrder(items: List<Item> = emptyList()) = Order(
    id = id,
    buyer = buyer,
    status = status.asDomainOrderStatus(),
    total = total.asDomainPrice(),
    items = items,
)

/**
 * Extension function to convert a [String] to a [Status] in the domain layer.
 *
 * @receiver the [String] representing the status
 * @return the corresponding [Status] in the domain layer
 */
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

/**
 * Extension function to convert a [Status] to a [String] in the database layer.
 *
 * @receiver the [Status] to be converted
 * @return the corresponding [String] in the database layer
 */
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

/**
 * Extension function to convert an [Order] to a [DbOrder] for database storage.
 *
 * @receiver the [Order] to be converted
 * @return the corresponding [DbOrder] for database storage
 */
fun Order.asDbOrder() = DbOrder(
    id = id,
    buyer = buyer,
    status = status.asDbOrderStatus(),
    total = total.value,
)
