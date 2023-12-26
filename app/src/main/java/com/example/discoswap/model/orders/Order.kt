package com.example.discoswap.model.orders

data class Order(
    var id: String,
    var buyer: String,
    var status: Status,
    var items: List<Item>,
)

enum class Status {
    NewOrder,
    BuyerContacted,
    InvoiceSent,
    PaymentPending,
    PaymentReceived,
    InProgress,
    Shipped,
    RefundSent,
    CancelledNonPayingBuyer,
    CancelledItemUnavailable,
    CancelledBuyerRequest,
}
