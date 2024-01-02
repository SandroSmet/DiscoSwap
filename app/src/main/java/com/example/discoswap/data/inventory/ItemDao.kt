package com.example.discoswap.data.inventory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.discoswap.data.message.DbMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbItem)

    @Query("SELECT * FROM items WHERE orderId = '0'")
    fun getInventory(): Flow<List<DbItem>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getInventoryItemDetails(id: String): Flow<DbItem>

    @Query("SELECT * FROM items WHERE orderId <> '0'")
    fun getOrderItems(): Flow<List<DbItem>>
}