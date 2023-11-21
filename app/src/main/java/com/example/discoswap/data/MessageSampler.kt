package com.example.discoswap.data

import com.example.discoswap.model.messages.Message
import com.example.discoswap.model.messages.Type

object MessageSampler {
    val messages = mutableListOf<Message>(
        Message(
            id = "1",
            name = "Sandro",
            text = "First message",
            type = Type.Order,
            read = false,
        ),
        Message(
            id = "2",
            name = "Dino",
            text = "Second message",
            type = Type.User,
            read = false,
        ),
        Message(
            id = "3",
            name = "Test",
            text = "Test message",
            type = Type.Other,
            read = true,
        ),
    )

    val getAll: () -> MutableList<Message> = {
        messages
    }
}
