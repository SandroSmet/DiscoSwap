package com.example.discoswap.ui.order.orderdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.discoswap.R
import com.example.discoswap.model.inventory.Item
import com.example.discoswap.model.order.Order
import com.example.discoswap.ui.order.OrderDetailApiState
import com.example.discoswap.ui.order.components.OrderInfo
import com.example.discoswap.ui.order.components.OrderItemInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    onBack: () -> Unit,
    orderDetailViewModel: OrderDetailViewModel = viewModel(factory = OrderDetailViewModel.Factory),
    onViewDetailClicked: (Item) -> Unit,
) {

    when (orderDetailViewModel.orderDetailApiState) {
        is OrderDetailApiState.Loading -> {
            Text(stringResource(R.string.loading_order_details))
        }

        is OrderDetailApiState.Error -> {
            Text(stringResource(R.string.error_loading_order_details))
        }

        is OrderDetailApiState.Success -> {
            val order = orderDetailViewModel.uiState.collectAsState().value.order!!
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(R.string.order_detail_from, order.buyer))
                        },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.Filled.ArrowBack, stringResource(R.string.menu_back))
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth(),
                ) {
                    item(order.id) {
                        OrderInfo(
                            order = order,
                        )

                        for (item in order.items) {
                            OrderItemInfo(
                                item = item,
                                onViewDetailClicked = onViewDetailClicked,
                            )
                        }

                        Text(
                            text = stringResource(R.string.order_detail_total, order.total.value),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
            }
        }
    }
}