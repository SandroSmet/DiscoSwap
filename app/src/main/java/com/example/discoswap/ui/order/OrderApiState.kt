package com.example.discoswap.ui.order

import com.example.discoswap.model.order.Order

sealed interface OrderApiState {
    object Error : OrderApiState
    object Loading : OrderApiState
    data class Success(val orders: List<Order>) : OrderApiState
}

sealed interface OrderDetailApiState {
    object Error : OrderDetailApiState
    object Loading : OrderDetailApiState
    data class Success(val order: Order) : OrderDetailApiState
}
