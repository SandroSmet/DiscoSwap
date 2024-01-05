package com.example.discoswap.ui.order


sealed interface OrderApiState {
    object Error : OrderApiState
    object Loading : OrderApiState
    object Success : OrderApiState
}

sealed interface OrderDetailApiState {
    object Error : OrderDetailApiState
    object Loading : OrderDetailApiState
    object Success : OrderDetailApiState
}
