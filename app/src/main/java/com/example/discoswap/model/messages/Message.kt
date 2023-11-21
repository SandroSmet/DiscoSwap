package com.example.discoswap.model.messages

data class Message(
    var id: String,
    var name: String,
    var text: String,
    var type: Type,
    var read: Boolean,
)

enum class Type {
    Order, User, WantList, Other,
}
