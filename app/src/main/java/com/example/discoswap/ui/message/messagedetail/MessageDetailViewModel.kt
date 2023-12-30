package com.example.discoswap.ui.message.messagedetail

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.Placeholder
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.discoswap.DiscoSwapApplication
import com.example.discoswap.data.message.MessageSampler
import com.example.discoswap.data.message.MessageRepository
import com.example.discoswap.model.message.Message
import com.example.discoswap.ui.DiscoSwapDestinationsArgs
import com.example.discoswap.ui.message.MessageDetailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MessageDetailViewModel(
    private val messagesRepository: MessageRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val messageIdAsString: String = savedStateHandle[DiscoSwapDestinationsArgs.MESSAGE_ID_ARG]!!

    var messageDetailApiState: MessageDetailApiState by mutableStateOf(MessageDetailApiState.Loading)
        private set

    private val _uiState = MutableStateFlow(
        MessageDetailState(),
    )

    val uiState: StateFlow<MessageDetailState> = _uiState.asStateFlow()

    lateinit var uiMessageState: StateFlow<Message?>

    init {
        loadMessage(messageIdAsString)
    }

    private fun loadMessage(id: String) {
        try {
            uiMessageState = messagesRepository.getMessageDetails(id)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = null,
                )
            messageDetailApiState = MessageDetailApiState.Success
        }
        catch (e: Exception) {
            MessageDetailApiState.Error
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DiscoSwapApplication)
                val messagesRepository = application.container.messageRepository
                MessageDetailViewModel(
                    messagesRepository = messagesRepository,
                    savedStateHandle = createSavedStateHandle(),
                )
            }
        }
    }
}
