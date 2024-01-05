package com.example.discoswap.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.discoswap.data.inventory.DbItem
import com.example.discoswap.data.inventory.ItemDao
import com.example.discoswap.data.message.DbMessage
import com.example.discoswap.data.message.MessageDao
import com.example.discoswap.data.order.DbOrder
import com.example.discoswap.data.order.OrderDao

/**
 * Room database class for the DiscoSwap application.
 */
@Database(
    entities = [
        DbMessage::class,
        DbItem::class,
        DbOrder::class,
    ], version = 1
)
abstract class DiscoSwapDatabase : RoomDatabase() {

    /**
     * Get the DAO interface with messages for database operations.
     *
     * @return the [MessageDao] interface
     */
    abstract fun messageDao(): MessageDao

    /**
     * Get the DAO interface with items for database operations.
     *
     * @return the [ItemDao] interface
     */
    abstract fun itemDao(): ItemDao

    /**
     * Get the DAO interface with orders for database operations.
     *
     * @return the [OrderDao] interface
     */
    abstract fun orderDao(): OrderDao
}
