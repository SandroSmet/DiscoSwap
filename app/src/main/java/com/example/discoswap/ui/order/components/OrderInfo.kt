package com.example.discoswap.ui.order.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.discoswap.R
import com.example.discoswap.model.order.Order

@Composable
fun OrderInfo(order: Order) {
    Text(
        text = stringResource(R.string.order_detail_order, order.id),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(8.dp),
    )
    Text(
        text = stringResource(
            R.string.order_detail_status,
            order.status.displayName
        ),
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(start = 8.dp),
    )
}