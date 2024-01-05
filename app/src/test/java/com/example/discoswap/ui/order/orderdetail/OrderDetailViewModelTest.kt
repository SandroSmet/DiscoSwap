package com.example.discoswap.ui.order.orderdetail

import androidx.lifecycle.SavedStateHandle
import com.example.discoswap.fake.FakeOrderRepository
import com.example.discoswap.helpers.TestDispatcherRule
import com.example.discoswap.ui.DiscoSwapDestinationsArgs
import com.example.discoswap.ui.order.OrderDetailApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OrderDetailViewModelTest {
    private lateinit var viewModel: OrderDetailViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun initializeViewModel() {
        viewModel = OrderDetailViewModel(
            FakeOrderRepository(),
            SavedStateHandle(
                initialState = mapOf(DiscoSwapDestinationsArgs.ORDER_ID_ARG to "12345")
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `OrderDetailApiState starts in Loading state`() {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        assert(viewModel.orderDetailApiState is OrderDetailApiState.Loading)
    }

    @Test
    fun `OrderDetailApiState updates to Success after loading`() {
        assert(viewModel.orderDetailApiState is OrderDetailApiState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ViewModel is initialized with the correct initial state`() {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        val initialState = viewModel.uiState.value

        assertEquals(null, initialState.order)
    }

    @Test
    fun `ViewModel uiState is updated with the correct state after loading`() {
        val updatedState = viewModel.uiState.value

        assertEquals("12345", updatedState.order!!.id)
    }
}