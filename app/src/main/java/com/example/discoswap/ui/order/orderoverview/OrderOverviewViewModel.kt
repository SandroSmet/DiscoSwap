package com.example.discoswap.ui.order.orderoverview

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
import com.example.discoswap.data.order.OrderRepository
import com.example.discoswap.ui.order.OrderApiState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderOverviewViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderOverviewState())
    val uiState: StateFlow<OrderOverviewState> = _uiState.asStateFlow()

    var orderApiState: OrderApiState by mutableStateOf(OrderApiState.Loading)
        private set

    init {
        refreshOrders()
        getRepoOrders()
    }

    private fun refreshOrders() {
        viewModelScope.launch {
            val refreshOrders = async { orderRepository.refresh() }
            try {
                awaitAll(refreshOrders)
            }
            catch (e: Exception) {
                orderApiState = OrderApiState.Error
            }
        }
    }

    private fun getRepoOrders() {
        viewModelScope.launch {
            orderRepository.getOrders()
                .catch {
                    orderApiState = OrderApiState.Error
                }
                .collect { orders ->
                    _uiState.update {
                        it.copy(
                            currentOrderList = orders,
                        )
                    }
                    orderApiState = OrderApiState.Success
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DiscoSwapApplication)
                val ordersRepository = application.container.orderRepository
                OrderOverviewViewModel(orderRepository = ordersRepository)
            }
        }
    }
}
