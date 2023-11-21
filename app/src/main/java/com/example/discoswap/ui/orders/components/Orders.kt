package com.example.discoswap.ui.orders.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.discoswap.model.orders.Order

@Composable
fun Orders(
    orders: List<Order>,
    onViewDetailClicked: (Order) -> Unit,
) {
    if (orders.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp),
        ) {
            items(orders) { order ->
                OrderListItem(
                    order,
                    onViewDetailClicked = { onViewDetailClicked(order) },
                )
            }
        }
    }
}
