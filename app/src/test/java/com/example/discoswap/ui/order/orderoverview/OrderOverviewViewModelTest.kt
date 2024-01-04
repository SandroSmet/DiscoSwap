package com.example.discoswap.ui.order.orderoverview

import com.example.discoswap.fake.FakeInventoryRepository
import com.example.discoswap.fake.FakeOrderRepository
import com.example.discoswap.helpers.TestDispatcherRule
import com.example.discoswap.ui.inventory.inventoryoverview.InventoryOverviewViewModel
import com.example.discoswap.ui.order.OrderApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `InventoryApiState updates to Success after loading`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        advanceUntilIdle()

        assert(viewModel.orderApiState is OrderApiState.Success)
    }

    @Test
    fun `ViewModel is initialized with the correct initial state`() = runTest {
        val initialState = viewModel.uiState.value

        assert(initialState.currentOrderList.isEmpty())
    }
}