package com.example.discoswap.data.inventory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.discoswap.model.inventory.Item
import com.example.discoswap.model.inventory.Release
import com.example.discoswap.model.order.Price

/**
 * Database entity representing an item in the inventory.
 *
 * @property id the unique identifier of the item
 * @property orderId the order ID associated with the item
 * @property price the price of the item
 * @property mediaCondition the media condition of the item
 * @property sleeveCondition the sleeve condition of the item
 * @property conditionComments additional comments on the condition of the item
 * @property itemLocation the location of the item
 * @property privateComments private comments associated with the item
 * @property releaseId the unique identifier of the associated release
 * @property releaseDescription the description of the associated release
 * @property releaseTitle the title of the associated release
 * @property releaseArtist the artist of the associated release
 * @property releaseFormat the format of the associated release
 * @property releaseThumbnail the thumbnail URL of the associated release
 */
@Entity(tableName = "items")
data class DbItem(
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

/**
 * Extension function to convert a [DbItem] to a [Item] in the domain layer.
 *
 * @receiver the [DbItem] to be converted
 * @return the corresponding [Item] in the domain layer
 */
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

/**
 * Extension function to convert a list of [DbItem] to a list of [Item] in the domain layer.
 *
 * @receiver the list of [DbItem] to be converted
 * @return the corresponding list of [Item] in the domain layer
 */
fun List<DbItem>.asDomainInventoryItems() = map { it.asDomainInventoryItems() }

/**
 * Extension function to convert a [Double] to a [Price] in the domain layer.
 *
 * @receiver the [Double] value representing the price
 * @return the corresponding [Price] in the domain layer with currency set to "EUR"
 */
fun Double.asDomainPrice() = Price(
    value = this,
    currency = "EUR",
)

/**
 * Extension function to convert an [Item] to a [DbItem] for database storage.
 *
 * @receiver the [Item] to be converted
 * @return the corresponding [DbItem] for database storage
 */
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
