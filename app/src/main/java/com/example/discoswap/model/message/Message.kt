package com.example.discoswap.model.message

data class Message(
    var id: String,
    var name: String,
    var text: String? = "",
    var subject: String,
    var type: Type,
    var read: Boolean,
    var orderId: String? = "",
)

enum class Type {
    Order, User, WantList, Other,
}
