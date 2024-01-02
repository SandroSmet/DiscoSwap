package com.example.discoswap.ui.order

import com.example.discoswap.model.order.Order

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
