package com.example.discoswap.ui.inventory.inventoryoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.discoswap.DiscoSwapApplication
import com.example.discoswap.data.inventory.InventoryRepository
import com.example.discoswap.ui.inventory.InventoryApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InventoryOverviewViewModel(
    private val inventoryRepository: InventoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(InventoryOverviewState())
    val uiState: StateFlow<InventoryOverviewState> = _uiState.asStateFlow()

    var inventoryApiState: InventoryApiState by mutableStateOf(InventoryApiState.Loading)
        private set

    init {
        getApiInventory()
    }

    private fun getApiInventory() {
        viewModelScope.launch {
            inventoryRepository.getInventory()
                .catch {
                    inventoryApiState = InventoryApiState.Error
                }
                .collect { inventory ->
                    _uiState.update {
                        it.copy(
                            currentInventoryItemList = inventory,
                        )
                    }
                    inventoryApiState = InventoryApiState.Success
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DiscoSwapApplication)
                val inventoryRepository = application.container.inventoryRepository
                InventoryOverviewViewModel(inventoryRepository = inventoryRepository)
            }
        }
    }
}
