package com.example.discoswap.ui.inventory.itemdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.discoswap.DiscoSwapApplication
import com.example.discoswap.data.inventory.InventoryRepository
import com.example.discoswap.ui.DiscoSwapDestinationsArgs
import com.example.discoswap.ui.inventory.InventoryItemDetailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemDetailViewModel (
    private val inventoryRepository: InventoryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val itemIdAsString: String = savedStateHandle[DiscoSwapDestinationsArgs.ITEM_ID_ARG]!!

    var inventoryItemDetailApiState: InventoryItemDetailApiState by mutableStateOf(InventoryItemDetailApiState.Loading)
        private set

    private val _uiState = MutableStateFlow(ItemDetailState())

    val uiState: StateFlow<ItemDetailState> = _uiState.asStateFlow()

    init {
        loadItem(itemIdAsString)
    }

    private fun loadItem(id: String) {
        viewModelScope.launch {
            inventoryRepository.getInventoryItemDetails(id)
                .catch {
                    inventoryItemDetailApiState = InventoryItemDetailApiState.Error
                }
                .collect { item ->
                    _uiState.update {
                        it.copy(
                            item = item,
                        )
                    }
                    inventoryItemDetailApiState = InventoryItemDetailApiState.Success
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DiscoSwapApplication)
                val inventoryRepository = application.container.inventoryRepository
                ItemDetailViewModel(
                    inventoryRepository = inventoryRepository,
                    savedStateHandle = createSavedStateHandle(),
                )
            }
        }
    }
}
