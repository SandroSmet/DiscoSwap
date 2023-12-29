package com.example.discoswap.ui.inventory.inventoryoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.model.inventory.Item
import com.example.discoswap.ui.inventory.InventoryApiState
import com.example.discoswap.ui.inventory.components.Items

@Composable
fun InventoryOverviewScreen(
    inventoryOverviewViewModel: InventoryOverviewViewModel = viewModel(factory = InventoryOverviewViewModel.Factory),
    onViewDetailClicked: (Item) -> Unit,
) {
    when (val inventoryApiState = inventoryOverviewViewModel.inventoryApiState) {
        is InventoryApiState.Loading -> {
            Text("Loading inventory from api...")
        }
        is InventoryApiState.Error -> {
            Text("Error loading inventory from api.")
        }
        is InventoryApiState.Success -> {
            val items = inventoryApiState.inventoryItems
            Items(
                items,
                onViewDetailClicked = onViewDetailClicked,
            )
        }
    }
}
