package com.example.discoswap.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.discoswap.model.message.Message
import com.example.discoswap.model.message.Type

@Entity(tableName = "messages")
data class DbMessage (
    @PrimaryKey
    var id: String,
    var name: String,
    var text: String? = "",
    var subject: String,
    var type: Type,
    var read: Boolean,
)

fun Message.asDbMessage(): DbMessage = DbMessage(
    id = id,
    name = name,
    text = text,
    subject = subject,
    type = type,
    read = read,
)

fun List<DbMessage>.asDomainMessages() = map { it.asDomainMessage() }

fun DbMessage.asDomainMessage() = Message(
    id = id,
    name = name,
    text = text,
    subject = subject,
    type = type,
    read = read,
)
