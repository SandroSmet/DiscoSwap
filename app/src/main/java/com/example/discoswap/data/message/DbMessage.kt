package com.example.discoswap.data.message

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.discoswap.model.message.Message
import com.example.discoswap.model.message.Type

/**
 * Database entity representing a message.
 *
 * @property id the unique identifier of the message
 * @property name the name associated with the message
 * @property text the text content of the message
 * @property subject the subject of the message
 * @property type the type of the message (e.g., INBOX, SENT)
 * @property read indicates whether the message has been read or not
 * @property orderId the order ID associated with the message (nullable)
 */
@Entity(tableName = "messages")
data class DbMessage(
    @PrimaryKey
    var id: String,
    var name: String,
    var text: String? = "",
    var subject: String,
    var type: Type,
    var read: Boolean,
    var orderId: String? = "",
)

/**
 * Extension function to convert a [Message] to a [DbMessage] for database storage.
 *
 * @receiver the [Message] to be converted
 * @return the corresponding [DbMessage] for database storage
 */
fun Message.asDbMessage(): DbMessage = DbMessage(
    id = id,
    name = name,
    text = text,
    subject = subject,
    type = type,
    read = read,
    orderId = orderId,
)

/**
 * Extension function to convert a list of [DbMessage] to a list of [Message] in the domain layer.
 *
 * @receiver the list of [DbMessage] to be converted
 * @return the corresponding list of [Message] in the domain layer
 */
fun List<DbMessage>.asDomainMessages() = map { it.asDomainMessage() }

/**
 * Extension function to convert a [DbMessage] to a [Message] in the domain layer.
 *
 * @receiver the [DbMessage] to be converted
 * @return the corresponding [Message] in the domain layer
 */
fun DbMessage.asDomainMessage() = Message(
    id = id,
    name = name,
    text = text,
    subject = subject,
    type = type,
    read = read,
    orderId = orderId,
)
