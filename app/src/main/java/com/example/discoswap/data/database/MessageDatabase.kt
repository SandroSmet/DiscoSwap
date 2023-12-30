package com.example.discoswap.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbMessage::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}