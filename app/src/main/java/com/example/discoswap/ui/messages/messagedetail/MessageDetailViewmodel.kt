package com.example.discoswap.ui.messages.messagedetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discoswap.data.MessageSampler
import com.example.discoswap.model.messages.Message
import com.example.discoswap.network.MessageApi
import com.example.discoswap.network.asDomainObject
import com.example.discoswap.network.asDomainObjects
import com.example.discoswap.ui.DiscoSwapDestinationsArgs
import com.example.discoswap.ui.messages.MessageApiState
import com.example.discoswap.ui.messages.MessageDetailApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageDetailViewModel @Inject constructor(
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
        getApiMessageDetail()
    }

    private fun getApiMessageDetail() {
        viewModelScope.launch {
            messageDetailApiState = try {
                val result = MessageApi.messageService.getMessageDetails(messageIdAsString)
                _uiState.update { it.copy(message = result.asDomainObject()) }
                MessageDetailApiState.Success(result.asDomainObject())
            } catch (e: Exception) {
                MessageDetailApiState.Error
            }
        }
    }
}
