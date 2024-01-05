package com.example.discoswap.ui.order.orderdetail

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
import com.example.discoswap.data.order.OrderRepository
import com.example.discoswap.ui.DiscoSwapDestinationsArgs
import com.example.discoswap.ui.order.OrderDetailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailViewModel(
    private val orderRepository: OrderRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val orderIdAsString: String = savedStateHandle[DiscoSwapDestinationsArgs.ORDER_ID_ARG]!!

    var orderDetailApiState: OrderDetailApiState by mutableStateOf(OrderDetailApiState.Loading)
        private set

    private val _uiState = MutableStateFlow(
        OrderDetailState(),
    )

    val uiState: StateFlow<OrderDetailState> = _uiState.asStateFlow()

    init {
        loadOrder(orderIdAsString)
    }

    private fun loadOrder(id: String) {
        viewModelScope.launch {
            orderRepository.refreshDetail(id)
            orderRepository.getOrderDetails(id)
                .catch {
                    orderDetailApiState = OrderDetailApiState.Error
                }
                .collect { order ->
                    _uiState.update {
                        it.copy(
                            order = order,
                        )
                    }
                    orderDetailApiState = OrderDetailApiState.Success
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DiscoSwapApplication)
                val ordersRepository = application.container.orderRepository
                OrderDetailViewModel(
                    orderRepository = ordersRepository,
                    savedStateHandle = createSavedStateHandle(),
                )
            }
        }
    }
}