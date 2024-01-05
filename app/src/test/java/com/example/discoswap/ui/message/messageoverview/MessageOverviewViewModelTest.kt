package com.example.discoswap.ui.message.messageoverview

import com.example.discoswap.fake.FakeMessageRepository
import com.example.discoswap.helpers.TestDispatcherRule
import com.example.discoswap.ui.message.MessageApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MessageOverviewViewModelTest {
    private lateinit var viewModel: MessageOverviewViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun initializeViewModel() {
        viewModel = MessageOverviewViewModel(FakeMessageRepository())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `MessageApiState starts in Loading state`() {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        assert(viewModel.messageApiState is MessageApiState.Loading)
    }

    @Test
    fun `MessageApiState updates to Success after loading`() {
        assert(viewModel.messageApiState is MessageApiState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ViewModel is initialized with the correct initial state`() {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        val initialState = viewModel.uiState.value

        assertEquals(0, initialState.currentMessageList.size)
    }

    @Test
    fun `ViewModel uiState is updated with the correct state after loading`() {
        val uiState = viewModel.uiState.value

        assertEquals(1, uiState.currentMessageList.size)
    }
}