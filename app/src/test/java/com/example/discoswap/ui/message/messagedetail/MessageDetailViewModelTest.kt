package com.example.discoswap.ui.message.messagedetail

import androidx.lifecycle.SavedStateHandle
import com.example.discoswap.fake.FakeMessageRepository
import com.example.discoswap.helpers.TestDispatcherRule
import com.example.discoswap.ui.DiscoSwapDestinationsArgs
import com.example.discoswap.ui.message.MessageDetailApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MessageDetailViewModelTest {
    private lateinit var viewModel: MessageDetailViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun initializeViewModel() {
        viewModel = MessageDetailViewModel(
            FakeMessageRepository(),
            SavedStateHandle(
                initialState = mapOf(DiscoSwapDestinationsArgs.MESSAGE_ID_ARG to "12345")
            ),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `MessageDetailApiState starts in Loading state`() {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        assert(viewModel.messageDetailApiState is MessageDetailApiState.Loading)
    }

    @Test
    fun `MessageDetailApiState updates to Success after loading`() {
        assert(viewModel.messageDetailApiState is MessageDetailApiState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ViewModel is initialized with the correct initial state`() {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        val initialState = viewModel.uiState.value

        assert(initialState.message == null)
    }

    @Test
    fun `ViewModel uiState is updated with the correct value after loading`() {
        val uiState = viewModel.uiState.value

        assert(uiState.message?.id == "12345")
    }
}