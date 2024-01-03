package com.example.discoswap.ui.order.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.discoswap.R
import com.example.discoswap.model.inventory.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderItemInfo(
    item: Item,
    onViewDetailClicked: (Item) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        ListItem(
            modifier = Modifier.clickable
            {
                onViewDetailClicked(item)
            },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            leadingContent = {
                AsyncImage(
                    model = item.release.thumbnail,
                    contentDescription = stringResource(R.string.item_thumbnail),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(1.2f)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                )
            },

            headlineText = {
                Text(
                    text = item.release.description,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            },

            supportingText = {
                Text(
                    text = stringResource(
                        R.string.order_detail_price,
                        item.price.value
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(
                        R.string.order_detail_media,
                        item.mediaCondition
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(
                        R.string.order_detail_sleeve,
                        item.sleeveCondition
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
        )
    }
}
