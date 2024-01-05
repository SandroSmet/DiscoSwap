package com.example.discoswap.data.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for handling operations related to orders in the database.
 */
@Dao
interface OrderDao {
    /**
     * Insert an order into the database.
     *
     * @param order the order to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: DbOrder)

    /**
     * Get the list of orders as a flow, including associated items.
     *
     * @return a Flow emitting the list of orders with associated items
     */
    @Query("SELECT * FROM orders")
    fun getOrders(): Flow<List<DbOrderWithItems>>

    /**
     * Get details of a specific order as a flow, including associated items.
     *
     * @param id the ID of the order
     * @return a Flow emitting the details of the order with the given ID and associated items
     */
    @Query("SELECT * FROM orders WHERE id = :id")
    fun getOrderDetails(id: String): Flow<DbOrderWithItems>
}
