package com.example.discoswap.ui

import androidx.compose.ui.input.key.Key
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.discoswap.ui.DiscoSwapDestinationsArgs.ITEM_ID_ARG
import com.example.discoswap.ui.DiscoSwapDestinationsArgs.MESSAGE_ID_ARG
import com.example.discoswap.ui.DiscoSwapDestinationsArgs.ORDER_ID_ARG
import com.example.discoswap.ui.DiscoSwapScreens.INVENTORY_SCREEN
import com.example.discoswap.ui.DiscoSwapScreens.ITEM_DETAIL_SCREEN
import com.example.discoswap.ui.DiscoSwapScreens.MESSAGES_SCREEN
import com.example.discoswap.ui.DiscoSwapScreens.MESSAGE_DETAIL_SCREEN
import com.example.discoswap.ui.DiscoSwapScreens.ORDERS_SCREEN
import com.example.discoswap.ui.DiscoSwapScreens.ORDER_DETAIL_SCREEN

/**
 * Screens used in [DiscoSwapDestinations]
 */
private object DiscoSwapScreens {
    const val MESSAGES_SCREEN = "messages"
    const val MESSAGE_DETAIL_SCREEN = "detailMessage"
    const val ORDERS_SCREEN = "orders"
    const val ORDER_DETAIL_SCREEN = "detailOrder"
    const val INVENTORY_SCREEN = "inventory"
    const val ITEM_DETAIL_SCREEN = "detailItem"
}

/**
 * Arguments used in [DiscoSwapDestinations] routes
 */
object DiscoSwapDestinationsArgs {
    const val MESSAGE_ID_ARG = "messageId"
    const val ORDER_ID_ARG = "orderId"
    const val ITEM_ID_ARG = "itemId"
}

/**
 * Destinations used in the [DiscoSwapNavGraph]
 */
object DiscoSwapDestinations {
    const val MESSAGES_ROUTE = MESSAGES_SCREEN
    const val MESSAGE_DETAIL_ROUTE = "$MESSAGE_DETAIL_SCREEN/{$MESSAGE_ID_ARG}"
    const val ORDERS_ROUTE = ORDERS_SCREEN
    const val ORDER_DETAIL_ROUTE = "$ORDER_DETAIL_SCREEN/{$ORDER_ID_ARG}"
    const val INVENTORY_ROUTE = INVENTORY_SCREEN
    const val ITEM_DETAIL_ROUTE = "$ITEM_DETAIL_SCREEN/{$ITEM_ID_ARG}"
}

/**
 * Models the navigation actions in the app.
 */
class DiscoSwapNavigationActions(private val navController: NavHostController) {
    fun navigateToMessages() {
        navigateToMenuItem(DiscoSwapDestinations.MESSAGES_ROUTE)
    }

    fun navigateToOrders() {
        navigateToMenuItem(DiscoSwapDestinations.ORDERS_ROUTE)
    }

    fun navigateToInventory() {
        navigateToMenuItem(DiscoSwapDestinations.INVENTORY_ROUTE)
    }

    fun navigateToMenuItem(route: String) {
        navController.navigate(route) {
            Key.F
            // Pop up to the start destination of the graph to avoid building up a large stack of destinations
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToMessageDetail(messageId: Int) {
        navController.navigate("$MESSAGE_DETAIL_SCREEN/$messageId")
    }

    fun navigateToOrderDetail(orderId: Int) {
        navController.navigate("$ORDER_DETAIL_SCREEN/$orderId")
    }

    fun navigateToItemDetail(itemId: Int) {
        navController.navigate("$ITEM_DETAIL_SCREEN/$itemId")
    }
}
