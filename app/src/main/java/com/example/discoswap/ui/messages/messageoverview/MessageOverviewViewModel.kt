package com.example.discoswap.ui.messages.messageoverview

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
import com.example.discoswap.data.MessageSampler
import com.example.discoswap.data.MessagesRepository
import com.example.discoswap.ui.messages.MessageApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageOverviewViewModel @Inject constructor(
    private val messagesRepository: MessagesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MessageOverviewState(MessageSampler.getAll()))
    val uiState: StateFlow<MessageOverviewState> = _uiState.asStateFlow()

    var messageApiState: MessageApiState by mutableStateOf(MessageApiState.Loading)
        private set

    init {
        getApiMessages()
    }

    private fun getApiMessages() {
        viewModelScope.launch {
            messageApiState = try {
                val result = messagesRepository.getMessages()
                _uiState.update { it.copy(currentMessageList = result) }
                MessageApiState.Success(result)
            } catch (e: Exception) {
                e.printStackTrace()
                MessageApiState.Error
            }
        }
    }
}
