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
import com.example.discoswap.data.inventory.ApiInventoryRepository
import com.example.discoswap.data.inventory.InventoryRepository
import com.example.discoswap.data.inventory.InventorySampler
import com.example.discoswap.data.message.MessageSampler
import com.example.discoswap.data.message.MessagesRepository
import com.example.discoswap.ui.inventory.InventoryApiState
import com.example.discoswap.ui.message.MessageApiState
import com.example.discoswap.ui.message.messageoverview.MessageOverviewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InventoryOverviewViewModel(
    private val inventoryRepository: InventoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(InventoryOverviewState(InventorySampler.getAll()))
    val uiState: StateFlow<InventoryOverviewState> = _uiState.asStateFlow()

    var inventoryApiState: InventoryApiState by mutableStateOf(InventoryApiState.Loading)
        private set

    init {
        getApiInventory()
    }

    private fun getApiInventory() {
        viewModelScope.launch {
            inventoryApiState = try {
                val result = inventoryRepository.getInventory()
                _uiState.update { it.copy(currentInventoryItemList = result) }
                InventoryApiState.Success(result)
            } catch (e: Exception) {
                e.printStackTrace()
                InventoryApiState.Error
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
