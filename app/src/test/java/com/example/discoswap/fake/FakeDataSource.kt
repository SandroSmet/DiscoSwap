package com.example.discoswap.fake

import com.example.discoswap.network.inventory.ApiInventoryItem
import com.example.discoswap.network.message.ApiMessageItem
import com.example.discoswap.network.message.ApiUser
import com.example.discoswap.network.order.ApiOrderDetail
import com.example.discoswap.network.order.ApiOrderItemDetail
import com.example.discoswap.network.order.ApiRelease
import com.example.discoswap.network.order.ApiValue

object FakeDataSource {
    private val release1 = ApiRelease(
        12345,
        "title",
        "description",
        "artist",
        "thumbnail",
        "format",
    )
    private val item1 = ApiInventoryItem(
        12345,
        "condition",
        "sleeveCondition",
        "comments",
        "location",
        ApiValue(5.00, "EUR"),
        release1
    )

    private val orderitem1 = ApiOrderItemDetail(
        123456,
        ApiValue(5.00, "EUR"),
        "MediaCondition",
        "SleeveCondition",
        "Comments",
        "Location",
        "Private Comments",
        release1,
    )

    private val order1 = ApiOrderDetail(
        "12345",
        ApiUser("username"),
        "status",
        ApiValue(5.00, "EUR"),
        listOf(orderitem1),
    )

    private val user1 = ApiUser("username")

    private val message1 = ApiMessageItem(
        "12345",
        "subject",
        "type",
        true,
        user1,
        "body",
        "12345"
    )

    val messages = listOf(message1)

    val inventory = listOf(item1)
    val orders = listOf(order1)

}