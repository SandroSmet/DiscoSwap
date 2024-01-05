package com.example.discoswap.ui.inventory.itemdetail

import com.example.discoswap.fake.FakeInventoryRepository
import com.example.discoswap.helpers.TestDispatcherRule
import com.example.discoswap.ui.inventory.InventoryApiState
import com.example.discoswap.ui.inventory.inventoryoverview.InventoryOverviewViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ItemDetailViewModelTest {
    private lateinit var viewModel: InventoryOverviewViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun initializeViewModel() {
        viewModel = InventoryOverviewViewModel(FakeInventoryRepository())
    }

    @Test
    fun `InventoryApiState starts in Loading state`() = runTest {
        assert(viewModel.inventoryApiState is InventoryApiState.Loading)
    }

    @Test
    fun `ViewModel is initialized with the correct initial state`() {
        val initialState = viewModel.uiState.value

        assert(initialState.currentInventoryItemList.isEmpty())
    }

}