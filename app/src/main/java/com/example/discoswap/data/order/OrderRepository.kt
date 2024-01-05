package com.example.discoswap.data.order

import com.example.discoswap.data.inventory.ItemDao
import com.example.discoswap.data.inventory.asDbItem
import com.example.discoswap.data.inventory.asDomainInventoryItems
import com.example.discoswap.model.order.Order
import com.example.discoswap.network.order.ApiOrderDetail
import com.example.discoswap.network.order.OrderApiService
import com.example.discoswap.network.order.asDomainObject
import com.example.discoswap.network.order.getOrderDetailsAsFlow
import com.example.discoswap.network.order.getOrdersAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

interface OrderRepository {
    suspend fun getOrders(): Flow<List<Order>>
    suspend fun getOrderDetails(orderId: String): Flow<Order>
    suspend fun refreshAll()
}

class CachingOrderRepository(
    private val orderDao: OrderDao,
    private val itemDao: ItemDao,
    private val orderApiService: OrderApiService
): OrderRepository {

    override suspend fun getOrders(): Flow<List<Order>> {
        return combine(orderDao.getOrders(), itemDao.getOrderItems()) {
                orders, orderItems ->
            orders.map { order ->
                order.order.asDomainOrder(orderItems.asDomainInventoryItems()).copy(
                    items = orders.find { it.order.id == order.order.id }!!.items.asDomainInventoryItems(),
                )
            }
        }
    }

    override suspend fun getOrderDetails(orderId: String): Flow<Order> {
        return orderDao.getOrderDetails(orderId).map { order ->
            order.order.asDomainOrder(order.items.asDomainInventoryItems())
        }
    }

    override suspend fun refreshAll() {
        orderApiService.getOrdersAsFlow().collect {
            for (order in it.orders) {
                insertOrderWithItems(order)
            }
        }
    }

    private suspend fun insertOrderWithItems(apiOrder: ApiOrderDetail) {
        for (item in apiOrder.items) {
            itemDao.insert(item.asDomainObject(apiOrder.id).asDbItem())
        }
        orderDao.insert(apiOrder.asDomainObject().asDbOrder())
    }
}

