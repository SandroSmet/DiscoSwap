package com.example.discoswap.ui.inventory.itemdetail

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
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.discoswap.ui.inventory.InventoryItemDetailApiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    onBack: () -> Unit,
    itemDetailViewModel: ItemDetailViewModel = viewModel(factory = ItemDetailViewModel.Factory)
) {

    when (itemDetailViewModel.inventoryItemDetailApiState) {
        is InventoryItemDetailApiState.Loading -> {
            Text("Loading item details from api...")
        }

        is InventoryItemDetailApiState.Error -> {
            Text("Error loading item details from api.")
        }

        is InventoryItemDetailApiState.Success -> {
            val item = itemDetailViewModel.uiState.collectAsState().value.item!!
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Item: #" + item.id)
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
                    item(item.id) {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                        ) {
                            Row {
                                AsyncImage(
                                    model = item.release.thumbnail,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(120.dp)
                                        .aspectRatio(1.2f)
                                        .clip(
                                            RoundedCornerShape(10.dp)
                                        )
                                        .padding(8.dp),
                                )
                                Text(
                                    text = item.release.description,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(8.dp),
                                )
                            }

                            Text(
                                text = "Media condition: " + item.mediaCondition,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp, 2.dp),
                            )
                            Text(
                                text = "Sleeve condition: " + item.sleeveCondition,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp, 2.dp),
                            )
                            Text(
                                text = "€" + item.price.value.toString(),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp, 4.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}