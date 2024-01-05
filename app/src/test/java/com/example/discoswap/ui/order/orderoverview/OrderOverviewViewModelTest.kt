package com.example.discoswap.ui.order.orderoverview

import com.example.discoswap.fake.FakeOrderRepository
import com.example.discoswap.helpers.TestDispatcherRule
import com.example.discoswap.ui.order.OrderApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OrderOverviewViewModelTest {
    private lateinit var viewModel: OrderOverviewViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun initializeViewModel() {
        viewModel = OrderOverviewViewModel(FakeOrderRepository())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `InventoryApiState starts in Loading state`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        assert(viewModel.orderApiState is OrderApiState.Loading)

    }

    @Test
    fun `InventoryApiState updates to Success after loading`() = runTest {
        assert(viewModel.orderApiState is OrderApiState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ViewModel is initialized with the correct initial state`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        val initialState = viewModel.uiState.value

        assertEquals(0, initialState.currentOrderList.size)
    }

    @Test
    fun `ViewModel uiState is updated with the correct state after loading`() = runTest {
        val updatedState = viewModel.uiState.value

        assertEquals(1, updatedState.currentOrderList.size)
    }
}