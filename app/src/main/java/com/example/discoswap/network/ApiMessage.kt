package com.example.discoswap.network

import com.example.discoswap.model.messages.Message
import com.example.discoswap.model.messages.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.annotation.Nullable

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
    )

fun List<ApiMessageItem>.asDomainObjects() =
    map {
        Message(
            id = it.id,
            subject = it.subject,
            text = it.body,
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
