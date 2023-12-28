package com.example.discoswap.data

import com.example.discoswap.model.orders.Order
import com.example.discoswap.network.OrderApiService
import com.example.discoswap.network.asDomainObject
import com.example.discoswap.network.asDomainObjects

interface OrdersRepository {
    suspend fun getOrders(): List<Order>
    suspend fun getOrderDetail(orderId: String): Order

}

class ApiOrdersRepository(
    private val ordersApiService: OrderApiService
): OrdersRepository {
    override suspend fun getOrders(): List<Order> {
        return ordersApiService.getOrders().orders.asDomainObjects()
    }

    override suspend fun getOrderDetail(orderId: String): Order {
        return ordersApiService.getOrderDetails(orderId).asDomainObject()
    }
}