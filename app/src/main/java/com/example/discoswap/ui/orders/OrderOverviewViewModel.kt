package com.example.discoswap.ui.orders

import androidx.lifecycle.ViewModel
import com.example.discoswap.data.OrderSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderOverviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderOverviewState(OrderSampler.getAll()))
    val uiState: StateFlow<OrderOverviewState> = _uiState.asStateFlow()
}
