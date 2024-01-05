package com.example.discoswap.ui.inventory.itemdetail

import androidx.lifecycle.SavedStateHandle
import com.example.discoswap.fake.FakeInventoryRepository
import com.example.discoswap.helpers.TestDispatcherRule
import com.example.discoswap.ui.DiscoSwapDestinationsArgs
import com.example.discoswap.ui.inventory.InventoryApiState
import com.example.discoswap.ui.inventory.InventoryItemDetailApiState
import com.example.discoswap.ui.inventory.inventoryoverview.InventoryOverviewViewModel
import com.example.discoswap.ui.order.OrderDetailApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ItemDetailViewModelTest {
    private lateinit var viewModel: ItemDetailViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun initializeViewModel() {
        viewModel = ItemDetailViewModel(
            FakeInventoryRepository(),
            SavedStateHandle(
                initialState = mapOf(DiscoSwapDestinationsArgs.ITEM_ID_ARG to "12345")
            ),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `InventoryItemDetailApiState starts in Loading state`() {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        assert(viewModel.inventoryItemDetailApiState is InventoryItemDetailApiState.Loading)
    }

    @Test
    fun `InventoryItemDetailApiState updates to Success after loading`() {
        assert(viewModel.inventoryItemDetailApiState is InventoryItemDetailApiState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ViewModel is initialized with the correct initial state`() {
        Dispatchers.setMain(StandardTestDispatcher())
        initializeViewModel()

        val initialState = viewModel.uiState.value

        assert(initialState.item == null)
    }

    @Test
    fun `ViewModel uiState is updated with the correct state after loading`() {
        val updatedState = viewModel.uiState.value

        assert(updatedState.item != null)
    }

}