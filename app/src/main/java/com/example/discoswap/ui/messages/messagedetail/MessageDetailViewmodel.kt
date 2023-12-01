package com.example.discoswap.ui.messages.messagedetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.discoswap.DiscoSwapApplication
import com.example.discoswap.data.MessageSampler
import com.example.discoswap.data.MessagesRepository
import com.example.discoswap.model.messages.Message
import com.example.discoswap.ui.DiscoSwapDestinationsArgs
import com.example.discoswap.ui.messages.MessageDetailApiState
import com.example.discoswap.ui.messages.messageoverview.MessageOverviewViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageDetailViewModel @Inject constructor(
    private val messagesRepository: MessagesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val messageIdAsString: String = savedStateHandle[DiscoSwapDestinationsArgs.MESSAGE_ID_ARG]!!

    var messageDetailApiState: MessageDetailApiState by mutableStateOf(MessageDetailApiState.Loading)
        private set

    private val _uiState = MutableStateFlow(
        MessageDetailState(MessageSampler.messages.find { message: Message -> message.id == "1" }!!),
    )

    val uiState: StateFlow<MessageDetailState> = _uiState.asStateFlow()

    init {
        loadMessage(messageIdAsString)
    }

    private fun loadMessage(id: String) {
        viewModelScope.launch {
            messageDetailApiState = try {
                val message = messagesRepository.getMessageDetails(id)
                _uiState.update { it.copy(message = message) }
                MessageDetailApiState.Success(message)
            } catch (e: Exception) {
                MessageDetailApiState.Error
            }
        }
    }
}
