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

/**
 * Repository interface for handling order operations.
 */
interface OrderRepository {
    /**
     * Get the list of orders as a flow.
     *
     * @return a Flow emitting the list of orders
     */
    suspend fun getOrders(): Flow<List<Order>>

    /**
     * Get details of a specific order as a flow.
     *
     * @param orderId the ID of the order
     * @return a Flow emitting the details of the order with the given ID
     */
    suspend fun getOrderDetails(orderId: String): Flow<Order>

    /**
     * Refresh all orders by fetching data from the remote service and updating the local repository.
     */
    suspend fun refreshAll()

    /**
     * Refresh details of a specific order by fetching data from the remote service and updating the local repository.
     *
     * @param orderId the ID of the order
     */
    suspend fun refreshDetail(orderId: String)
}

/**
 * Implementation of [OrderRepository] with caching capabilities.
 *
 * @property orderDao the DAO for accessing local order database
 * @property itemDao the DAO for accessing local inventory item database
 * @property orderApiService the remote order API service
 */
class CachingOrderRepository(
    private val orderDao: OrderDao,
    private val itemDao: ItemDao,
    private val orderApiService: OrderApiService
) : OrderRepository {

    /**
     * Get the list of orders as a flow, including associated items.
     *
     * @return a Flow emitting the list of orders with associated items
     */
    override suspend fun getOrders(): Flow<List<Order>> {
        return combine(orderDao.getOrders(), itemDao.getOrderItems()) { orders, orderItems ->
            orders.map { order ->
                order.order.asDomainOrder(orderItems.asDomainInventoryItems()).copy(
                    items = orders.find { it.order.id == order.order.id }!!.items.asDomainInventoryItems(),
                )
            }
        }
    }

    /**
     * Get details of a specific order as a flow, including associated items.
     *
     * @param orderId the ID of the order
     * @return a Flow emitting the details of the order with the given ID and associated items
     */
    override suspend fun getOrderDetails(orderId: String): Flow<Order> {
        return orderDao.getOrderDetails(orderId).map { order ->
            order.order.asDomainOrder(order.items.asDomainInventoryItems())
        }
    }

    /**
     * Refresh all orders by fetching data from the remote service and updating the local repository.
     */
    override suspend fun refreshAll() {
        orderApiService.getOrdersAsFlow().collect {
            for (order in it.orders) {
                insertOrderWithItems(order)
            }
        }
    }

    /**
     * Refresh details of a specific order by fetching data from the remote service and updating the local repository.
     *
     * @param orderId the ID of the order
     */
    override suspend fun refreshDetail(orderId: String) {
        orderApiService.getOrderDetailsAsFlow(orderId).collect {
            insertOrderWithItems(it)
        }
    }

    /**
     * Helper method to insert an order with its associated items into the local repository.
     *
     * @param apiOrder the order details from the remote service
     */
    private suspend fun insertOrderWithItems(apiOrder: ApiOrderDetail) {
        for (item in apiOrder.items) {
            itemDao.insert(item.asDomainObject(apiOrder.id).asDbItem())
        }
        orderDao.insert(apiOrder.asDomainObject().asDbOrder())
    }
}
