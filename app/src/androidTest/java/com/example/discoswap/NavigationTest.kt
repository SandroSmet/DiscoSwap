package com.example.discoswap

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.discoswap.ui.DiscoSwapNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            DiscoSwapNavGraph(navController = navController)
        }
    }

    @Test
    fun `Messages tab icon is displayed at startup`() {
        composeTestRule
            .onNodeWithText(getResourceString(R.string.title_messages))
            .assertIsDisplayed()
    }

    @Test
    fun `Orders tab icon is displayed at startup`() {
        composeTestRule
            .onNodeWithText(getResourceString(R.string.title_orders))
            .assertIsDisplayed()
    }

    @Test
    fun `Inventory tab icon is displayed at startup`() {
        composeTestRule
            .onNodeWithText(getResourceString(R.string.title_inventory))
            .assertIsDisplayed()
    }

    @Test
    fun `Messages are displayed when clicked on messages tab`() {
        composeTestRule
            .onNodeWithText(getResourceString(R.string.title_messages))
            .performClick()

        composeTestRule.waitUntil(WAIT_UNTIL_TIMEOUT) {
            composeTestRule
                .onAllNodesWithTag("navigateToMessageDetail")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodesWithTag("navigateToMessageDetail")[0]
            .assertIsDisplayed()
    }

    @Test
    fun `Orders are displayed when clicked on orders tab`() {
        composeTestRule
            .onNodeWithText(getResourceString(R.string.title_orders))
            .performClick()

        composeTestRule.waitUntil(WAIT_UNTIL_TIMEOUT) {
            composeTestRule
                .onAllNodesWithTag("navigateToOrderDetail")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodesWithTag("navigateToOrderDetail")[0]
            .assertIsDisplayed()
    }

    @Test
    fun `Inventory is displayed when clicked on inventory tab`() {
        composeTestRule
            .onNodeWithText(getResourceString(R.string.title_inventory))
            .performClick()

        composeTestRule.waitUntil(WAIT_UNTIL_TIMEOUT) {
            composeTestRule
                .onAllNodesWithTag("navigateToInventoryDetail")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodesWithTag("navigateToInventoryDetail")[0]
            .assertIsDisplayed()
    }

    @Test
    fun `Message details are shown when clicked on message`() {
        composeTestRule
            .onNodeWithText(getResourceString(R.string.title_messages))
            .performClick()

        composeTestRule.waitUntil(WAIT_UNTIL_TIMEOUT) {
            composeTestRule
                .onAllNodesWithTag("navigateToMessageDetail")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodesWithTag("navigateToMessageDetail")[0]
            .performClick()

        composeTestRule.waitUntil(WAIT_UNTIL_TIMEOUT) {
            composeTestRule
                .onAllNodesWithTag("messageDetail")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodesWithTag("messageDetail")[0]
            .assertIsDisplayed()
    }

    private fun getResourceString(@StringRes id: Int): String {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        return context.resources.getString(id)
    }

    companion object {
        private const val WAIT_UNTIL_TIMEOUT = 10_000L
    }
}