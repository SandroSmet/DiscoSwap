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
import com.example.discoswap.ui.order.OrderDetailApiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    onBack: () -> Unit,
    orderDetailViewModel: OrderDetailViewModel = viewModel(factory = OrderDetailViewModel.Factory),
    onViewDetailClicked: (Item) -> Unit,
) {

    when (orderDetailViewModel.orderDetailApiState) {
        is OrderDetailApiState.Loading -> {
            Text("Loading order details...")
        }

        is OrderDetailApiState.Error -> {
            Text("Error loading order details.")
        }

        is OrderDetailApiState.Success -> {
            val order = orderDetailViewModel.uiState.collectAsState().value.order!!
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(R.string.message_username) + " " + order.buyer)
                        },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.Filled.ArrowBack, "Menu back")
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
                        Text(
                            text = "Order #" + order.id,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(8.dp),
                        )
                        Text(
                            text = "Status: " + order.status.displayName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp),
                        )
                        for (item in order.items) {
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
                                            contentDescription = null,
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
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(bottom = 4.dp),
                                        )
                                    },

                                    supportingText = {
                                        Text(
                                            text = "Price: €" + item.price.value,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                        Text(
                                            text = "Media: " + item.mediaCondition,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                        Text(
                                            text = "Sleeve: " + item.sleeveCondition,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    },
                                )
                            }
                        }

                        Text(
                            text = "Total: €" + order.total.value.toString(),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
            }
        }
    }

}