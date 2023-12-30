package com.example.discoswap.data.order

import com.example.discoswap.model.order.Order
import com.example.discoswap.network.order.OrderApiService
import com.example.discoswap.network.order.asDomainObject
import com.example.discoswap.network.order.asDomainObjects

interface OrderRepository {
    suspend fun getOrders(): List<Order>
    suspend fun getOrderDetail(orderId: String): Order

}

class ApiOrderRepository(
    private val ordersApiService: OrderApiService
): OrderRepository {
    override suspend fun getOrders(): List<Order> {
        return ordersApiService.getOrders().orders.asDomainObjects()
    }

    override suspend fun getOrderDetail(orderId: String): Order {
        return ordersApiService.getOrderDetails(orderId).asDomainObject()
    }
}