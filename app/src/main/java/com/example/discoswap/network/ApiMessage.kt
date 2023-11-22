package com.example.discoswap.network

import com.example.discoswap.model.messages.Message
import com.example.discoswap.model.messages.Type
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
)

@Serializable
data class ApiUser(
    val username: String,
)

fun List<ApiMessageItem>.asDomainObjects() =
    map {
        Message(
            id = it.id,
            text = it.subject,
            name = it.user.username,
            type = when (it.type) {
                "order-notification" -> Type.Order
                "wantlist-notification" -> Type.WantList
                "user-message" -> Type.User
                else -> Type.Other
            },
            read = it.read,
        )
    }
