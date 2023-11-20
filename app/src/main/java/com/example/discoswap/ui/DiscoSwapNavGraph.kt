package com.example.discoswap.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.discoswap.R
import com.example.discoswap.ui.messages.MessageOverviewScreen
import com.example.discoswap.ui.navigation.BottomNavigationBar
import com.example.discoswap.ui.navigation.NavigationMenuItem

@Composable
fun DiscoSwapNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = DiscoSwapDestinations.MESSAGES_ROUTE,
    navActions: DiscoSwapNavigationActions = remember(navController) {
        DiscoSwapNavigationActions(navController)
    },
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    val messagesMenuItem = NavigationMenuItem(
        route = DiscoSwapDestinations.MESSAGES_ROUTE,
        title = R.string.title_messages,
        Icons.Filled.Mail,
        navigationAction = { navActions.navigateToMessages() },
    )
    val ordersMenuItem = NavigationMenuItem(
        route = DiscoSwapDestinations.ORDERS_ROUTE,
        title = R.string.title_orders,
        Icons.Outlined.Payments,
        navigationAction = {
            navActions.navigateToOrders()
        },
    )
    val inventoryMenuItem = NavigationMenuItem(
        route = DiscoSwapDestinations.INVENTORY_ROUTE,
        title = R.string.title_inventory,
        Icons.Filled.Inventory,
        navigationAction = {
            navActions.navigateToInventory()
        },
    )

    val menuItems = arrayOf(messagesMenuItem, ordersMenuItem, inventoryMenuItem)

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(DiscoSwapDestinations.MESSAGES_ROUTE) {
            MenuScaffold(currentRoute = currentRoute, menuItems) {
                MessageOverviewScreen(onViewDetailClicked = { message -> navActions.navigateToMessageDetail(message.id) })
            }
        }
//        composable(AdminDestinations.ORDERS_ROUTE) {
//            MenuScaffold(currentRoute = currentRoute, menuItems) {
//                OrdersOverview()
//            }
//        }
//        composable(AdminDestinations.SUPPLEMENTS_ROUTE) {
//            MenuScaffold(currentRoute = currentRoute, menuItems) {
//                SupplementsOverview()
//            }
//        }
//        composable(AdminDestinations.CALENDER_ROUTE) {
//            MenuScaffold(currentRoute = currentRoute, menuItems) {
//                CalendarOverview()
//            }
//        }
//        composable(
//            AdminDestinations.QUOTATION_DETAIL_ROUTE,
//        ) {
//            QuotationDetailScreen(
//                onBack = { navController.popBackStack() },
//            )
//        }
//        composable(
//            AdminDestinations.QUOTATION_EDIT_ROUTE,
//        ) {
//            QuotationEditScreen(
//                onBack = { navController.popBackStack() },
//            )
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScaffold(currentRoute: String, menuItems: Array<NavigationMenuItem>, content: @Composable () -> Unit) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentRoute, menuItems)
        },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            content()
        }
    }
}