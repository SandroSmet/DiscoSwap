package com.example.discoswap.network.message

import com.example.discoswap.model.message.Message
import com.example.discoswap.model.message.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMessages(
    val items: List<ApiMessageItem>,
)

@Serializable
data class ApiMessageItem(
    val id: String,
    val subject: String,
    val type: String,
    val read: Boolean,
    @SerialName("from_user")
    val user: ApiUser,
    val body: String?,
    @SerialName("order_id")
    val orderId: String?,
)

@Serializable
data class ApiUser(
    val username: String,
)

fun ApiMessageItem.asDomainObject() =
    Message(
        id = this.id,
        subject = this.subject,
        text = this.body,
        name = this.user.username,
        type = when (this.type) {
            "order-notification" -> Type.Order
            "wantlist-notification" -> Type.WantList
            "user-message" -> Type.User
            else -> Type.Other
        },
        read = this.read,
        orderId = this.orderId,
    )