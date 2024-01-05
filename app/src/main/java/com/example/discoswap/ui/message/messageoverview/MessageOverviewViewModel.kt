package com.example.discoswap.ui.message.messageoverview

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
import com.example.discoswap.data.message.MessageRepository
import com.example.discoswap.ui.inventory.InventoryApiState
import com.example.discoswap.ui.message.MessageApiState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MessageOverviewViewModel(
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MessageOverviewState())
    val uiState: StateFlow<MessageOverviewState> = _uiState.asStateFlow()

    var messageApiState: MessageApiState by mutableStateOf(MessageApiState.Loading)
        private set

    init {
        refreshMessages()
        getRepoMessages()
    }

    private fun refreshMessages() {
        viewModelScope.launch {
            val refreshMessages = async { messageRepository.refresh() }
            try {
                awaitAll(refreshMessages)
            }
            catch (e: Exception) {
                messageApiState = MessageApiState.Error
            }
        }
    }

    private fun getRepoMessages() {
        viewModelScope.launch {
            messageRepository.getMessages()
                .catch {
                    messageApiState = MessageApiState.Error
                }
                .collect { messages ->
                    _uiState.update {
                        it.copy(
                            currentMessageList = messages,
                        )
                    }
                    messageApiState = MessageApiState.Success
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DiscoSwapApplication)
                val messagesRepository = application.container.messageRepository
                MessageOverviewViewModel(messageRepository = messagesRepository)
            }
        }
    }
}
