package com.combocart.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetailHomeScreen(
    navController: NavController,
    viewModel: RetailViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ComboCart", color = White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = White)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Notifications.route) }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryOrange)
            )
        },
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF9F9F9)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Search Bar & Filter
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Search for stores, ingredients...", fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = MediumGray) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = White,
                            focusedContainerColor = White,
                            unfocusedBorderColor = Color(0xFFEEEEEE)
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(PrimaryOrange, RoundedCornerShape(12.dp))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Filter", tint = White)
                    }
                }
            }

            // Location
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, tint = PrimaryOrange, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Current Location", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        "Change", 
                        color = PrimaryOrange, 
                        fontSize = 14.sp, 
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { navController.navigate(Screen.LocationSetup.route) }
                    )
                }
            }

            // Categories Chips
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    RetailCategoryChip("All Stores", isSelected = true)
                    RetailCategoryChip("Groceries", isSelected = false)
                    RetailCategoryChip("Fresh Produce", isSelected = false)
                }
            }

            // Map Placeholder
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.LocationOn, null, tint = MediumGray, modifier = Modifier.size(48.dp))
                            Text("Map View Placeholder", color = MediumGray)
                        }
                        
                        // Nearby Badge
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                                .background(White, RoundedCornerShape(20.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).background(Color.Green, CircleShape))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("15 stores nearby", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                        // Re-center button
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp)
                                .size(40.dp)
                                .background(White, CircleShape)
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Refresh, null, tint = PrimaryOrange)
                        }
                    }
                }
            }

            // Nearby Stores Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Nearby Stores", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("View Map", color = PrimaryOrange, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Store Items
            items(state.stores) { store ->
                RetailStoreItem(
                    store = StoreData(
                        name = store.name,
                        rating = store.rating,
                        reviews = store.reviews,
                        distance = store.distance,
                        time = store.time,
                        status = store.status,
                        iconBg = store.iconBg
                    ),
                    onViewStore = {
                        navController.navigate(Screen.RetailStoreDetail.createRoute(store.id))
                    }
                )
            }

            // Register Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryOrange)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Store, null, tint = White, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Own a Retail Store?", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "Partner with ComboCart and reach\nthousands of customers", 
                            color = White.copy(alpha = 0.9f), 
                            fontSize = 14.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate(Screen.RetailRegister.route) },
                            colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = PrimaryOrange),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Register Your Store", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun RetailCategoryChip(text: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) PrimaryOrange else Color(0xFFEEEEEE))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { }
    ) {
        Text(
            text = text, 
            color = if (isSelected) White else DarkGray, 
            fontSize = 14.sp, 
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun RetailStoreItem(store: StoreData, onViewStore: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Store Icon
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(store.iconBg, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.ShoppingCart, null, tint = PrimaryOrange, modifier = Modifier.size(32.dp))
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(store.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                if (store.status == "Open") Color(0xFF4CAF50) else Color.Red, 
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(store.status, color = White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = YellowRating, modifier = Modifier.size(14.dp))
                    Text(" ${store.rating} ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("(${store.reviews})", fontSize = 12.sp, color = MediumGray)
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row {
                    Icon(Icons.Default.LocationOn, null, tint = MediumGray, modifier = Modifier.size(14.dp))
                    Text(" ${store.distance}  •  ", fontSize = 12.sp, color = MediumGray)
                    Text(store.time, fontSize = 12.sp, color = MediumGray)
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onViewStore,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("View Store", fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.Default.Share, null, tint = PrimaryOrange, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

data class StoreData(
    val name: String,
    val rating: Double,
    val reviews: String,
    val distance: String,
    val time: String,
    val status: String,
    val iconBg: Color
)
