package com.example.discoswap.network.message

import com.example.discoswap.model.message.Message
import com.example.discoswap.model.message.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the response structure for messages data from the remote API.
 *
 * @property items the list of message items
 */
@Serializable
data class ApiMessages(
    val items: List<ApiMessageItem>,
)

/**
 * Represents an individual message item in the remote API response.
 *
 * @property id the unique identifier of the message item
 * @property subject the subject of the message
 * @property type the type of the message
 * @property read indicates whether the message has been read
 * @property user the user associated with the message
 * @property body the body of the message
 * @property orderId the order ID associated with the message
 */
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

/**
 * Represents a user in the remote API response.
 *
 * @property username the username of the user
 */
@Serializable
data class ApiUser(
    val username: String,
)

/**
 * Extension function to convert an [ApiMessageItem] to a [Message] in the domain layer.
 *
 * @receiver the [ApiMessageItem] to be converted
 * @return the corresponding [Message] in the domain layer
 */
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
