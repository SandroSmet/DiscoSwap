package com.example.discoswap.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.discoswap.data.inventory.DbItem
import com.example.discoswap.data.inventory.ItemDao
import com.example.discoswap.data.message.DbMessage
import com.example.discoswap.data.message.MessageDao

@Database(
    entities = [
        DbMessage::class,
        DbItem::class,
    ], version = 1)
abstract class DiscoSwapDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun itemDao(): ItemDao
}