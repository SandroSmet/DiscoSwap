package com.example.discoswap.ui.message.messagedetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.discoswap.DiscoSwapApplication
import com.example.discoswap.data.message.MessageRepository
import com.example.discoswap.ui.DiscoSwapDestinationsArgs
import com.example.discoswap.ui.message.MessageDetailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MessageDetailViewModel(
    private val messageRepository: MessageRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val messageIdAsString: String = savedStateHandle[DiscoSwapDestinationsArgs.MESSAGE_ID_ARG]!!

    var messageDetailApiState: MessageDetailApiState by mutableStateOf(MessageDetailApiState.Loading)
        private set

    private val _uiState = MutableStateFlow(
        MessageDetailState(),
    )

    val uiState: StateFlow<MessageDetailState> = _uiState.asStateFlow()

    init {
        loadMessage(messageIdAsString)
    }

    private fun loadMessage(id: String) {
        viewModelScope.launch {
            messageRepository.getMessageDetails(id)
                .catch {
                    messageDetailApiState = MessageDetailApiState.Error
                }
                .collect { message ->
                    _uiState.update {
                        it.copy(
                            message = message,
                        )
                    }
                    messageDetailApiState = MessageDetailApiState.Success
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DiscoSwapApplication)
                val messagesRepository = application.container.messageRepository
                MessageDetailViewModel(
                    messageRepository = messagesRepository,
                    savedStateHandle = createSavedStateHandle(),
                )
            }
        }
    }
}
