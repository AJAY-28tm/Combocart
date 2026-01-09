package com.combocart.ui.checkout

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.cart.BillDetails
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.combocart.ui.cart.CartViewModel

@Composable
fun CheckoutScreen(
    navController: NavController,
    cartViewModel: CartViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    checkoutViewModel: CheckoutViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val cartState by cartViewModel.state.collectAsState()
    val checkoutState by checkoutViewModel.state.collectAsState()
    
    val cart = cartState.cart
    val items = cart?.items ?: emptyList()
    val total = cart?.total_price ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // Header
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Proceed to Checkout", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            item {
                AddressSection(checkoutState.selectedAddress)
            }
            
            // Coupons Section
            item {
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            Text("Coupons ", fontWeight = FontWeight.Bold)
                            androidx.compose.foundation.layout.Box(
                                modifier = Modifier
                                    .background(Color.Red, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text("🎟️", fontSize = 10.sp, color = White)
                            }
                        }
                        Text("View All >", color = PrimaryOrange, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Add ₹ 201 more for FREE delivery!",
                        fontSize = 12.sp,
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    androidx.compose.material3.LinearProgressIndicator(
                        progress = (total / 800.0).toFloat().coerceIn(0f, 1f),
                        modifier = Modifier.fillMaxWidth().height(6.dp),
                        color = Color(0xFF4CAF50),
                        trackColor = White
                    )
                }
            }
            
            // Cart Items
            items.forEach { item ->
                item {
                    CheckoutItemRow(item)
                }
            }
            
            // Reusing Bill Details logic with real total
            item {
                BillDetails(total)
            }
        }

        // Payment CTA
        Button(
            onClick = { navController.navigate(Screen.Payment.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Proceed to Payment ₹$total", color = White)
        }
    }
}

@Composable
fun AddressSection(address: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, null, tint = PrimaryOrange)
            Text("  Delivery Address", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text("Change", color = PrimaryOrange, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Home", fontWeight = FontWeight.Bold)
        Text(address, fontSize = 14.sp, color = MediumGray)
    }
}

@Composable
fun CheckoutItemRow(item: com.combocart.data.remote.CartItemDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .background(White, RoundedCornerShape(12.dp))
    ) {
        // Recipe Image Placeholder
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color(0xFFEEEEEE), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (!item.recipe?.image_url.isNullOrEmpty()) {
                coil.compose.AsyncImage(
                    model = item.recipe?.image_url,
                    contentDescription = item.recipe?.title,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
        }
        
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                item.recipe?.title ?: "Unknown Item",
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
            Text(
                "₹${item.recipe?.price ?: 0.0}",
                fontWeight = FontWeight.Bold,
                color = PrimaryOrange,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            
            // Quantity display
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Qty: ${item.quantity}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        // Delete icon
        IconButton(onClick = { /* Handle delete */ }) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Red,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
