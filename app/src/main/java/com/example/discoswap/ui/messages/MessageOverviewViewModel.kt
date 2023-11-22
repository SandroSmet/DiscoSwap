package com.example.discoswap.ui.messages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discoswap.data.MessageSampler
import com.example.discoswap.network.MessageApi.messageService
import com.example.discoswap.network.asDomainObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessageOverviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MessageOverviewState(MessageSampler.getAll()))
    val uiState: StateFlow<MessageOverviewState> = _uiState.asStateFlow()

    var messageApiState: MessageApiState by mutableStateOf(MessageApiState.Loading)
        private set

    init {
        getApiMessages()
    }

    private fun getApiMessages() {
        viewModelScope.launch {
            try {
                val result = messageService.getMessages()
                messageApiState = MessageApiState.Success(result.items.asDomainObjects())
                println("The messages: ${result.items}")
            } catch (e: Exception) {
                messageApiState = MessageApiState.Error
            }
        }
    }
}
