package com.example.discoswap.data

import android.content.Context
import androidx.room.Room
import com.example.discoswap.data.inventory.CachingInventoryRepository
import com.example.discoswap.data.inventory.InventoryRepository
import com.example.discoswap.data.inventory.ItemDao
import com.example.discoswap.data.message.CachingMessageRepository
import com.example.discoswap.data.message.MessageDao
import com.example.discoswap.data.message.MessageRepository
import com.example.discoswap.data.order.CachingOrderRepository
import com.example.discoswap.data.order.OrderDao
import com.example.discoswap.data.order.OrderRepository
import com.example.discoswap.network.inventory.InventoryApiService
import com.example.discoswap.network.message.MessageApiService
import com.example.discoswap.network.order.OrderApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency injection container for providing repositories and other dependencies.
 */
interface AppContainer {
    /**
     * Repository for handling message operations.
     */
    val messageRepository: MessageRepository

    /**
     * Repository for handling order operations.
     */
    val orderRepository: OrderRepository

    /**
     * Repository for handling inventory operations.
     */
    val inventoryRepository: InventoryRepository
}

/**
 * Default implementation of [AppContainer].
 *
 * @property applicationContext the application context
 */
@OptIn(ExperimentalSerializationApi::class)
private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

/**
 * Default implementation of [AppContainer].
 *
 * @property applicationContext the application context
 */
class DefaultAppContainer(
    private val applicationContext: Context
) : AppContainer {

    private val baseUrl = "https://api.discogs.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val database: DiscoSwapDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            DiscoSwapDatabase::class.java,
            "discoswap_database"
        ).build()
    }

    private val messageDao: MessageDao by lazy {
        database.messageDao()
    }

    /**
     * Repository for handling message operations.
     */
    override val messageRepository: MessageRepository by lazy {
        CachingMessageRepository(
            messageDao = messageDao,
            messageApiService = retrofit.create(MessageApiService::class.java)
        )
    }

    private val orderDao: OrderDao by lazy {
        database.orderDao()
    }

    private val itemDao: ItemDao by lazy {
        database.itemDao()
    }

    /**
     * Repository for handling order operations.
     */
    override val orderRepository: OrderRepository by lazy {
        CachingOrderRepository(
            orderDao = orderDao,
            itemDao = itemDao,
            orderApiService = retrofit.create(OrderApiService::class.java),
        )
    }

    /**
     * Repository for handling inventory operations.
     */
    override val inventoryRepository: InventoryRepository by lazy {
        CachingInventoryRepository(
            itemDao = itemDao,
            inventoryApiService = retrofit.create(InventoryApiService::class.java)
        )
    }
}
