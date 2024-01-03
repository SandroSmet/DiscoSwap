package com.example.discoswap.ui.inventory.inventoryoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.R
import com.example.discoswap.model.inventory.Item
import com.example.discoswap.ui.inventory.InventoryApiState
import com.example.discoswap.ui.inventory.components.Items

@Composable
fun InventoryOverviewScreen(
    inventoryOverviewViewModel: InventoryOverviewViewModel = viewModel(factory = InventoryOverviewViewModel.Factory),
    onViewDetailClicked: (Item) -> Unit,
) {
    when (inventoryOverviewViewModel.inventoryApiState) {
        is InventoryApiState.Loading -> {
            Text(stringResource(R.string.loading_inventory))
        }
        is InventoryApiState.Error -> {
            Text(stringResource(R.string.error_loading_inventory))
        }
        is InventoryApiState.Success -> {
            val items = inventoryOverviewViewModel.uiState.collectAsState().value.currentInventoryItemList
            Items(
                items,
                onViewDetailClicked = onViewDetailClicked,
            )
        }
    }
}
