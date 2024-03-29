package com.example.discoswap.ui.inventory.itemdetail

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.discoswap.R
import com.example.discoswap.ui.inventory.InventoryItemDetailApiState
import com.example.discoswap.ui.inventory.components.AllItemInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    onBack: () -> Unit,
    itemDetailViewModel: ItemDetailViewModel = viewModel(factory = ItemDetailViewModel.Factory)
) = when (itemDetailViewModel.inventoryItemDetailApiState) {
    is InventoryItemDetailApiState.Loading -> {
        Text(stringResource(R.string.loading_item_details))
    }

    is InventoryItemDetailApiState.Error -> {
        Text(stringResource(R.string.error_loading_item_details))
    }

    is InventoryItemDetailApiState.Success -> {
        val item = itemDetailViewModel.uiState.collectAsState().value.item!!
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.item_detail_id, item.id))
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
                item(item.id) {
                    AsyncImage(
                        model = item.release.thumbnail,
                        contentDescription = stringResource(R.string.item_thumbnail),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(200.dp)
                            .aspectRatio(1.2f)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp),
                    )

                    AllItemInfo(item)
                }
            }
        }
    }
}