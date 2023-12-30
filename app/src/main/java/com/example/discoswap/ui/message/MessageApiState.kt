package com.example.discoswap.ui.message

import com.example.discoswap.model.message.Message

sealed interface MessageApiState {
    object Error : MessageApiState
    object Loading : MessageApiState
    object Success : MessageApiState
}

sealed interface MessageDetailApiState {
    object Error : MessageDetailApiState
    object Loading : MessageDetailApiState
    object Success : MessageDetailApiState
}
