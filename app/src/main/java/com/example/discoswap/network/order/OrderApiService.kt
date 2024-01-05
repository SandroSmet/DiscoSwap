package com.example.discoswap.network.order

import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit service interface for handling API requests related to orders.
 */
interface OrderApiService {
    /**
     * Retrieves a list of orders from the remote API.
     *
     * @return [ApiOrders] representing the list of orders
     */
    @GET("marketplace/orders?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi&sort=last_activity&sort_order=desc&per_page=50")
    suspend fun getOrders(): ApiOrders

    /**
     * Retrieves detailed information about a specific order from the remote API.
     *
     * @param id the unique identifier of the order
     * @return [ApiOrderDetail] representing the detailed information about the order
     */
    @GET("marketplace/orders/{id}?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi")
    suspend fun getOrderDetails(@Path("id") id: String): ApiOrderDetail
}

/**
 * Extension function to convert the result of [getOrders] to a flow.
 *
 * @receiver the [OrderApiService] instance
 * @return a flow emitting [ApiOrders] representing the list of orders
 */
fun OrderApiService.getOrdersAsFlow() = flow { emit(getOrders()) }

/**
 * Extension function to convert the result of [getOrderDetails] to a flow.
 *
 * @receiver the [OrderApiService] instance
 * @param id the unique identifier of the order
 * @return a flow emitting [ApiOrderDetail] representing detailed information about the order
 */
fun OrderApiService.getOrderDetailsAsFlow(id: String) = flow { emit(getOrderDetails(id)) }
