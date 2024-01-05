package com.example.discoswap.data.inventory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.discoswap.data.message.DbMessage
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for handling operations related to items in the inventory database.
 */
@Dao
interface ItemDao {
    /**
     * Insert an item into the database.
     *
     * @param item the item to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbItem)

    /**
     * Get the list of inventory items as a flow.
     *
     * @return a Flow emitting the list of inventory items with orderId = '0'
     */
    @Query("SELECT * FROM items WHERE orderId = '0'")
    fun getInventory(): Flow<List<DbItem>>

    /**
     * Get details of a specific inventory item as a flow.
     *
     * @param id the ID of the inventory item
     * @return a Flow emitting the details of the inventory item with the given ID
     */
    @Query("SELECT * FROM items WHERE id = :id")
    fun getInventoryItemDetails(id: String): Flow<DbItem>

    /**
     * Get the list of items associated with orders as a flow.
     *
     * @return a Flow emitting the list of inventory items with orderId <> '0'
     */
    @Query("SELECT * FROM items WHERE orderId <> '0'")
    fun getOrderItems(): Flow<List<DbItem>>
}
