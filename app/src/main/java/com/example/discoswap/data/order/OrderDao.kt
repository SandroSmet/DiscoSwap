package com.example.discoswap.data.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: DbOrder)

    @Query("SELECT * FROM orders")
    fun getOrders(): Flow<List<DbOrderWithItems>>

    @Query("SELECT * FROM orders WHERE id = :id")
    fun getOrderDetails(id: String): Flow<DbOrderWithItems>
}