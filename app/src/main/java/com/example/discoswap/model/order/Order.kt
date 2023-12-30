package com.example.discoswap.model.order

import com.example.discoswap.model.inventory.Item

data class Order(
    var id: String,
    var buyer: String,
    var status: Status,
    var total: Price,
    var items: List<Item>,
)

enum class Status(val displayName: String) {
    NewOrder("New Order"),
    BuyerContacted("Buyer Contacted"),
    InvoiceSent("Invoice Sent"),
    PaymentPending("Payment Pending"),
    PaymentReceived("Payment Received"),
    InProgress("In Progress"),
    Shipped("Shipped"),
    RefundSent("Refund Sent"),
    CancelledNonPayingBuyer("Cancelled - Non-Paying Buyer"),
    CancelledItemUnavailable("Cancelled - Item Unavailable"),
    CancelledBuyerRequest("Cancelled - Buyer Request"),
    Merged("Merged"),
}
