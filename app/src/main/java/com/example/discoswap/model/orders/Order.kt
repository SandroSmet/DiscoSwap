package com.example.discoswap.model.orders

import com.example.discoswap.model.users.User

data class Order(
    var id: String,
    var buyer: User,
    var seller: User,
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
