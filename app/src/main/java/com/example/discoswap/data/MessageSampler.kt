package com.example.discoswap.data

import com.example.discoswap.model.messages.Message

object MessageSampler {
    val messages = mutableListOf<Message>(
        Message(
            id = 1,
            name = "Sandro",
            text = "First message",
        ),
        Message(
            id = 2,
            name = "Dino",
            text = "Second message",
        ),
        Message(
            id = 3,
            name = "Test",
            text = "Test message",
        ),
    )

    val getAll: () -> MutableList<Message> = {
        messages
    }
}
