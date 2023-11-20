package com.example.discoswap.ui.messages

import androidx.lifecycle.ViewModel
import com.example.discoswap.data.MessageSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MessageOverviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MessageOverviewState(MessageSampler.getAll()))
    val uiState: StateFlow<MessageOverviewState> = _uiState.asStateFlow()
}
