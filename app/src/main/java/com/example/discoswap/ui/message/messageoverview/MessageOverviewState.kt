package com.example.discoswap.ui.message.messageoverview

import com.example.discoswap.model.message.Message

data class MessageOverviewState(
    val currentMessageList: List<Message> = emptyList(),
)
