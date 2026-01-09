package com.combocart.ui.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.navigation.Screen
import com.combocart.ui.orders.OrdersViewModel
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.GreenSuccess
import com.combocart.ui.theme.LightGray
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackOrderScreen(
    navController: NavController,
    viewModel: OrdersViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val order = state.currentOrder

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Track Order", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Call Support */ }) {
                        Icon(Icons.Default.Call, null, tint = PrimaryOrange)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
            )
        }
    ) { padding ->
        if (state.isLoading && order == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryOrange)
            }
        } else if (order != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF8F9FA))
            ) {
                // Map Section
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                        val userPos = LatLng(12.9716, 77.5946)
                        val cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(userPos, 14f)
                        }
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState
                        ) {
                            Marker(state = MarkerState(position = userPos))
                        }

                        // ETA Chip
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.TopCenter),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = White)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.size(8.dp).background(GreenSuccess, CircleShape))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Arriving in ", fontSize = 12.sp, color = MediumGray)
                                Text("12 mins", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryOrange)
                            }
                        }
                    }
                }

                // Progress Steps
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StatusStep("Confirmed", true)
                                StatusLine(true)
                                StatusStep("Preparing", true)
                                StatusLine(true)
                                StatusStep("On the way", true, isCurrent = true)
                                StatusLine(false)
                                StatusStep("Delivered", false)
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFE3F2FD), RoundedCornerShape(12.dp))
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Text("Your order is on the way!", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Text(
                                        "Delivery partner has picked up your order and is heading to your location.",
                                        fontSize = 13.sp,
                                        color = MediumGray
                                    )
                                }
                            }
                        }
                    }
                }

                // Delivery Partner
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Delivery Partner", fontWeight = FontWeight.Bold, color = MediumGray, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(50.dp).background(LightGray, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Person, null, modifier = Modifier.size(30.dp), tint = MediumGray)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Rajesh Kumar", fontWeight = FontWeight.Bold)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Star, null, tint = Color(0xFFFFB300), modifier = Modifier.size(14.dp))
                                        Text(" 4.8 (245 deliveries)", fontSize = 12.sp, color = MediumGray)
                                    }
                                    Text("🏍 KA 05 MN 1234", fontSize = 12.sp, color = MediumGray)
                                }
                                Row {
                                    IconButton(
                                        onClick = { /* Call */ },
                                        modifier = Modifier.size(40.dp).background(Color(0xFFE3F2FD), CircleShape)
                                    ) {
                                        Icon(Icons.Default.Call, null, tint = PrimaryOrange, modifier = Modifier.size(20.dp))
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(
                                        onClick = { /* Message */ },
                                        modifier = Modifier.size(40.dp).background(Color(0xFFE8F5E9), CircleShape)
                                    ) {
                                        Icon(Icons.Default.Message, null, tint = GreenSuccess, modifier = Modifier.size(20.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Delivery Address
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Delivery Address", fontWeight = FontWeight.Bold, color = MediumGray, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Row {
                                Box(
                                    modifier = Modifier.size(36.dp).background(Color(0xFFE8F5E9), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Home, null, tint = GreenSuccess, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Home", fontWeight = FontWeight.Bold)
                                    Text(
                                        order.address_line,
                                        fontSize = 12.sp,
                                        color = MediumGray,
                                        lineHeight = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Change Address", color = PrimaryOrange, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                // Order Summary
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Order Details", fontWeight = FontWeight.Bold, color = MediumGray, fontSize = 14.sp)
                                Text("#${order.id.takeLast(8).uppercase()}", fontSize = 12.sp, color = MediumGray)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Display all order items
                            if (order.items.isNotEmpty()) {
                                order.items.forEach { item ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)).background(LightGray),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (!item.recipe?.image_url.isNullOrEmpty()) {
                                                coil.compose.AsyncImage(
                                                    model = item.recipe?.image_url,
                                                    contentDescription = item.recipe_title,
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                                )
                                            } else {
                                                Icon(Icons.Default.Fastfood, null, tint = MediumGray)
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(item.recipe_title, fontWeight = FontWeight.Bold)
                                            Text("₹${item.price_per_unit} × ${item.quantity}", fontSize = 12.sp, color = MediumGray)
                                        }
                                    }
                                }
                            } else {
                                // Fallback if no items
                                Text("Order items loading...", fontSize = 12.sp, color = MediumGray)
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(color = LightGray.copy(alpha = 0.5f))
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Estimated delivery", fontSize = 12.sp, color = MediumGray)
                                Text("8:45 PM - 9:00 PM", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Live Updates
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Live Updates", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            LiveUpdateItem("Your delivery partner is 2.5 km away", "2 mins ago", PrimaryOrange)
                            LiveUpdateItem("Order picked up from restaurant", "8 mins ago", GreenSuccess)
                            LiveUpdateItem("Order is ready for pickup", "12 mins ago", GreenSuccess)
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Oops! Order detail not found.", color = MediumGray)
            }
        }
    }
}

@Composable
fun StatusStep(title: String, isDone: Boolean, isCurrent: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    if (isDone) (if (isCurrent) PrimaryOrange else GreenSuccess) else Color.LightGray.copy(alpha=0.5f), 
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                if (isCurrent) {
                    Icon(Icons.Default.DirectionsBike, null, tint = White, modifier = Modifier.size(14.dp))
                } else {
                    Icon(Icons.Default.Check, null, tint = White, modifier = Modifier.size(14.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            title, 
            fontSize = 10.sp, 
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
            color = if (isDone) (if (isCurrent) PrimaryOrange else Black) else MediumGray
        )
    }
}

@Composable
fun StatusLine(isDone: Boolean) {
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(2.dp)
            .background(if (isDone) GreenSuccess else Color.LightGray.copy(alpha=0.5f))
            .padding(bottom = 12.dp)
    )
}

@Composable
fun LiveUpdateItem(text: String, time: String, dotColor: Color) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Box(modifier = Modifier.padding(top = 6.dp).size(8.dp).background(dotColor, CircleShape))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(time, fontSize = 11.sp, color = MediumGray)
        }
    }
}
