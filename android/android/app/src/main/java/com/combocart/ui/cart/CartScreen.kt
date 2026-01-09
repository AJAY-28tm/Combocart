package com.combocart.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.DarkGray
import com.combocart.ui.theme.GreenSuccess
import com.combocart.ui.theme.LightGray
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.RedAlert
import com.combocart.ui.theme.White
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.CircularProgressIndicator
import com.combocart.data.remote.CartItemDto


@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val cart = state.cart
    val items = cart?.items ?: emptyList()

    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White)
        ) {
            // Header
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (items.isNotEmpty()) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
                Text(
                    text = if (items.isEmpty()) "Your Cart" else "Proceed to Checkout",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            if (state.isLoading && cart == null) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                      CircularProgressIndicator(color = PrimaryOrange)
                 }
            } else if (items.isEmpty()) {
                EmptyCartView(navController)
            } else {
                FilledCartView(navController, items, cart?.total_price ?: 0.0, viewModel)
            }
        }
    }
}

@Composable
fun EmptyCartView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Panda Chef PlaceHolder
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(LightGray, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("🐼 Panda Chef")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("THERE ARE NO DISHES IN CART", fontWeight = FontWeight.Bold)
        Text("Our panda chef is waiting for your order!", color = MediumGray)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { navController.navigate(Screen.Home.route) },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Browse Combos")
        }
    }
}

@Composable
fun FilledCartView(
    navController: NavController, 
    cartItems: List<CartItemDto>, 
    serverTotal: Double,
    viewModel: CartViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Coupons / Free Delivery
        Column(modifier = Modifier.padding(16.dp).background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(12.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Coupons 🎟️", fontWeight = FontWeight.Bold)
                Text("View All >", color = PrimaryOrange, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Add ₹ 201 more for FREE delivery!", fontSize = 12.sp, color = GreenSuccess)
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = 0.6f,
                modifier = Modifier.fillMaxWidth().height(6.dp),
                color = GreenSuccess,
                trackColor = White
            )
        }

        // Items List
        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
        ) {
            items(cartItems) { item ->
                CartItemRow(
                    item = item, 
                    onDelete = { viewModel.removeFromCart(item.recipe_id) }, 
                    onUpdateQty = { delta -> viewModel.updateQuantity(item.recipe_id, delta) }
                )
            }
            
            item {
                BillDetails(serverTotal)
            }
        }

        // Bottom CTA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(16.dp), // Shadow normally here
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
             Column {
                 Text("Total Amount", fontSize = 12.sp, color = MediumGray)
                 Text("₹$serverTotal", fontSize = 20.sp, fontWeight = FontWeight.Bold)
             }
             
             Button(
                 onClick = { navController.navigate(Screen.Checkout.route) },
                 colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                 shape = RoundedCornerShape(8.dp)
             ) {
                 Text("Proceed to Checkout", color = White)
             }
        }
    }
}

@Composable
fun CartItemRow(item: CartItemDto, onDelete: () -> Unit, onUpdateQty: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .background(White, RoundedCornerShape(12.dp))
    ) {
        // Recipe Image (with coil or placeholder)
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (!item.recipe?.image_url.isNullOrEmpty()) {
                coil.compose.AsyncImage(
                    model = item.recipe?.image_url,
                    contentDescription = item.recipe?.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(item.recipe?.title ?: "Unknown Item", fontWeight = FontWeight.SemiBold, color = Black)
            Text("₹${item.recipe?.price ?: 0.0}", fontWeight = FontWeight.Bold, color = PrimaryOrange, modifier = Modifier.padding(vertical = 4.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Quantity Selector
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                        .padding(horizontal = 4.dp)
                ) {
                    IconButton(
                        onClick = { onUpdateQty(-1) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove, 
                            contentDescription = "Decrease",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Text(
                        text = item.quantity.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    
                    IconButton(
                        onClick = { onUpdateQty(1) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add, 
                            contentDescription = "Increase",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete, 
                        contentDescription = "Delete", 
                        tint = RedAlert,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BillDetails(total: Double) {
    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        Text("Bill Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))
        
        BillRow("Item Total", "₹$total")
        BillRow("Delivery Fee", "₹0") // Simplified
        Divider(modifier = Modifier.padding(vertical = 12.dp))
        BillRow("Total", "₹$total", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BillRow(label: String, value: String, color: Color = Black, fontWeight: FontWeight = FontWeight.Normal) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MediumGray)
        Text(value, color = color, fontWeight = fontWeight)
    }
}
