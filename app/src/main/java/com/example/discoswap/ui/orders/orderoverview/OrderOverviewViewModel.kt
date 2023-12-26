package com.example.discoswap.ui.orders.orderoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.discoswap.DiscoSwapApplication
import com.example.discoswap.data.OrderSampler
import com.example.discoswap.data.OrdersRepository
import com.example.discoswap.ui.messages.MessageApiState
import com.example.discoswap.ui.orders.OrderApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderOverviewViewModel(
    private val ordersRepository: OrdersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderOverviewState(OrderSampler.getAll()))
    val uiState: StateFlow<OrderOverviewState> = _uiState.asStateFlow()

    var orderApiState: OrderApiState by mutableStateOf(OrderApiState.Loading)
        private set

    init {
        getApiOrders()
    }

    private fun getApiOrders() {
        viewModelScope.launch {
            orderApiState = try {
                val result = ordersRepository.getOrders()
                _uiState.update { it.copy(currentOrderList = result) }
                OrderApiState.Success(result)
            } catch (e: Exception) {
                e.printStackTrace()
                OrderApiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DiscoSwapApplication)
                val ordersRepository = application.container.ordersRepository
                OrderOverviewViewModel(ordersRepository = ordersRepository)
            }
        }
    }
}
