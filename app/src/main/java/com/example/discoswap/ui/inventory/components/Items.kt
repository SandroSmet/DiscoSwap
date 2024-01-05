package com.example.discoswap.ui.inventory.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.discoswap.model.inventory.Item

@Composable
fun Items(
    items: List<Item>,
    onViewDetailClicked: (Item) -> Unit,
) {
    if (items.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp),
        ) {
            items(items) { item ->
                ItemListItem(
                    item,
                    onViewDetailClicked = { onViewDetailClicked(item) },
                )
            }
        }
    }
}
