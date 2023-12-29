package com.example.discoswap.ui.inventory.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        ListItem(
            modifier = Modifier.clickable
            {
                onViewDetailClicked(item)
            },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            headlineText = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    AsyncImage(model = item.release.thumbnail, contentDescription = null)
                    Text(
                        item.release.description,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            },
            supportingText = {
                Text("€" + item.price.value, Modifier.padding(3.dp))
            },
        )
        Divider()
    }
}
