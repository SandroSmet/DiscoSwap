package com.example.discoswap.data.inventory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.discoswap.model.inventory.Item
import com.example.discoswap.model.inventory.Release
import com.example.discoswap.model.order.Price

@Entity(tableName = "items")
data class DbItem (
    @PrimaryKey
    var id: Long,
    var orderId: String = "0",
    var price: Double,
    var mediaCondition: String,
    var sleeveCondition: String,
    var conditionComments: String,
    var itemLocation: String,
    var privateComments: String,
    var releaseId: Int,
    var releaseDescription: String,
    var releaseTitle: String,
    var releaseArtist: String,
    var releaseFormat: String,
    var releaseThumbnail: String,
)

fun DbItem.asDomainInventoryItems() = Item(
    id = id,
    price = price.asDomainPrice(),
    mediaCondition = mediaCondition,
    sleeveCondition = sleeveCondition,
    conditionComments = conditionComments,
    itemLocation = itemLocation,
    privateComments = privateComments,
    release = Release(
        id = releaseId,
        description = releaseDescription,
        title = releaseTitle,
        artist = releaseArtist,
        format = releaseFormat,
        thumbnail = releaseThumbnail,
    ),
)

fun List<DbItem>.asDomainInventoryItems() = map { it.asDomainInventoryItems() }

fun Double.asDomainPrice() = Price(
    value = this,
    currency = "EUR",
)

fun Item.asDbItem() = DbItem(
    id = id,
    orderId = orderId,
    price = price.value,
    mediaCondition = mediaCondition,
    sleeveCondition = sleeveCondition,
    conditionComments = conditionComments,
    itemLocation = itemLocation,
    privateComments = privateComments,
    releaseId = release.id,
    releaseDescription = release.description,
    releaseTitle = release.title,
    releaseArtist = release.artist,
    releaseFormat = release.format,
    releaseThumbnail = release.thumbnail,
)