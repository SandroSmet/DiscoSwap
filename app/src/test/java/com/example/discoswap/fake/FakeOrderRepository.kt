package com.example.discoswap.fake

import com.example.discoswap.data.order.OrderRepository
import com.example.discoswap.model.order.Order
import com.example.discoswap.network.order.asDomainObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeOrderRepository : OrderRepository {
    override suspend fun getOrders(): Flow<List<Order>> = flow {
        emit(FakeDataSource.orders.map { order ->
            order.asDomainObject() })
    }

    override suspend fun getOrderDetails(orderId: String): Flow<Order> {
        return flow {
            emit(FakeDataSource.orders.find { it.id == orderId }!!.asDomainObject())
        }
    }

    override suspend fun refreshAll() {
        // Not needed
    }

}
