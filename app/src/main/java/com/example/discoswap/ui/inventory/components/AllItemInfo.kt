package com.example.discoswap.ui.inventory.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.discoswap.R
import com.example.discoswap.model.inventory.Item

@Composable
fun AllItemInfo(
    item: Item
) {
    ItemInfo(
        stringResource(
            R.string.item_detail_release,
            item.release.artist,
            item.release.title
        ),
        modifier = Modifier.padding(8.dp),
    )

    ItemInfo(
        stringResource(R.string.item_detail_media_condition, item.mediaCondition),
        Modifier.padding(8.dp, 2.dp),
    )

    ItemInfo(
        stringResource(R.string.item_detail_sleeve_condition, item.sleeveCondition),
        Modifier.padding(8.dp, 2.dp),
    )

    ItemInfo(
        stringResource(R.string.item_detail_price, item.price.value),
        Modifier.padding(8.dp, 4.dp),
    )

    if (item.conditionComments.isNotBlank()){
        ItemInfo(
            stringResource(
                R.string.item_detail_public_comments,
                item.conditionComments
            ),
            Modifier.padding(8.dp, 2.dp),
        )
    }


    if (item.privateComments.isNotBlank()){
        ItemInfo(
            stringResource(
                R.string.item_detail_private_comments,
                item.privateComments
            ),
            Modifier.padding(8.dp, 2.dp),
        )
    }

    if (item.itemLocation.isNotBlank()){
        ItemInfo(
            stringResource(R.string.item_detail_location, item.itemLocation),
            Modifier.padding(8.dp, 2.dp),
        )
    }
}