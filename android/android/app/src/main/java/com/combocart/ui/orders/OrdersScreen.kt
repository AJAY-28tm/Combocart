package com.combocart.ui.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.GreenSuccess
import com.combocart.ui.theme.LightGray
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

import androidx.compose.runtime.collectAsState
import com.combocart.ui.orders.OrdersViewModel
import androidx.compose.foundation.layout.Arrangement

@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrdersViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Active Orders", "Past Orders")
    val state by viewModel.state.collectAsState()

    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White)
        ) {
            Text(
                "My Orders",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = White,
                contentColor = PrimaryOrange,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = PrimaryOrange
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            if (state.isLoading) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                      androidx.compose.material3.CircularProgressIndicator(color = PrimaryOrange)
                 }
            } else if (state.error != null) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                      Text("Error: ${state.error}", color = Color.Red)
                 }
            } else {
                val activeOrders = state.orders.filter { 
                    it.status == "PENDING" || it.status == "PROCESSING" || 
                    it.status == "CONFIRMED" || it.status == "PREPARING" || 
                    it.status == "OUT_FOR_DELIVERY" || it.status == "SHIPPED"
                }
                val pastOrders = state.orders.filter { it.status == "COMPLETED" || it.status == "CANCELLED" || it.status == "DELIVERED" }
                val displayOrders = if (selectedTab == 0) activeOrders else pastOrders

                if (displayOrders.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No orders found", color = MediumGray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(displayOrders) { order ->
                            if (selectedTab == 0) {
                                ActiveOrderItem(navController, order)
                            } else {
                                PastOrderItem(order)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActiveOrderItem(navController: NavController, order: com.combocart.data.remote.OrderDto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Column {
                Text("Order #${order.id.takeLast(6)}", fontWeight = FontWeight.Bold)
                Text(order.address_line, fontSize = 12.sp, color = MediumGray, maxLines = 1)
            }
            Text("₹${order.total_amount}", fontWeight = FontWeight.Bold, color = PrimaryOrange)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
             Box(modifier = Modifier.size(8.dp).background(GreenSuccess, CircleShape))
             Spacer(modifier = Modifier.width(8.dp))
             Text(order.status, color = GreenSuccess, fontWeight = FontWeight.SemiBold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { navController.navigate(Screen.TrackOrder.createRoute(order.id)) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Track Order", color = White)
        }
    }
}

@Composable
fun PastOrderItem(order: com.combocart.data.remote.OrderDto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Column {
                Text("Order #${order.id.takeLast(6)}", fontWeight = FontWeight.Bold)
                Text("Status: ${order.status}", fontSize = 12.sp, color = MediumGray)
                Text(order.created_at.take(10), fontSize = 10.sp, color = MediumGray)
            }
            Text("₹${order.total_amount}", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
             OutlinedButton(
                 onClick = { /* Reorder logic */ },
                 shape = RoundedCornerShape(8.dp),
                 modifier = Modifier.weight(1f)
             ) {
                 Text("Reorder", color = PrimaryOrange)
             }
             Spacer(modifier = Modifier.width(8.dp))
             OutlinedButton(
                 onClick = { /* Rate Order */ },
                 shape = RoundedCornerShape(8.dp),
                 modifier = Modifier.weight(1f)
             ) {
                 Text("Rate Food", color = Black)
             }
        }
    }
}
