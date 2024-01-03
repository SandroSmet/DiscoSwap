package com.example.discoswap.ui.inventory.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
fun ItemListItem(
    item: Item,
    onViewDetailClicked: (Item) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
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
                    text = stringResource(
                        R.string.item_overview_artist_title,
                        item.release.artist,
                        item.release.title
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            },
            supportingText = {
                Text(
                    text = stringResource(R.string.item_overview_price, item.price.value),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(R.string.item_overview_media, item.mediaCondition),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(R.string.item_overview_sleeve, item.sleeveCondition),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },

        )
        Divider()
    }
}
