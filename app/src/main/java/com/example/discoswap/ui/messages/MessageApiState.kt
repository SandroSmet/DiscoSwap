package com.example.discoswap.ui.messages

import com.example.discoswap.model.messages.Message

sealed interface MessageApiState {
    object Error : MessageApiState
    object Loading : MessageApiState
    data class Success(val messages: List<Message>) : MessageApiState
}
