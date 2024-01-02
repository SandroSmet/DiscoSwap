package com.example.discoswap.ui.order.orderoverview

import com.example.discoswap.model.order.Order

data class OrderOverviewState(
    val currentOrderList: List<Order> = emptyList(),
)
